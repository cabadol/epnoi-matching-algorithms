package es.upm.oeg.epnoi.matching.metrics

import es.upm.oeg.epnoi.matching.metrics.feature.TFExtractor
import es.upm.oeg.epnoi.matching.metrics.topics.LDAAlgorithm
import org.apache.log4j.{Level, Logger}
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.mutable

/**
 * Created by cbadenes on 20/04/15.
 */
object ContentSimilarityExample {

  def main(args: Array[String]): Unit = {

    /***********************************
      * Spark Context
      ***********************************/
    val conf = new SparkConf().setMaster("local").setAppName("Content Similarity Example")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)

    /***********************************
      * Load corpus
      ***********************************/
    val files: RDD[(String,String)]= sc.wholeTextFiles("src/test/resources/paper-corpus/*.txt")
    val corpus = files.map(_._2)

    /***********************************
      * Normalization: Tokens
      ***********************************/
    val tokenized : RDD[Seq[String]] =
      corpus.map(_.toLowerCase.split("\\s")).
        map(_.filter(_.length > 3).
        filter(_.forall(java.lang.Character.isLetter))
        filter(!StandardAnalyzer.STOP_WORDS_SET.contains(_)))

    tokenized.collect().foreach(x => println("Tokenized: " + x))

    /***********************************
      * Normalization: Terms and Frequencies (String,Long)
      ***********************************/
    val termCounts: Array[(String, Long)] =
      tokenized.flatMap(_.map(_ -> 1L)).reduceByKey(_ + _).collect().sortBy(-_._2)

    println("Terms & Frequency:")
    termCounts.foreach(x=>print(x+","))
    println()

    /***********************************
      * Normalization: Vocabulary
      ***********************************/
    // Remove common terms (20%)
    val numStopwords = 20 * termCounts.size / 100
    val vocabArray: Array[String] = termCounts.takeRight(termCounts.size - numStopwords).map(_._1)
    // All terms
    //val vocabArray: Array[String] = termCounts.map(_._1)

    println("Vocabulary:")
    vocabArray.foreach(x=>print(x+","))

    // Map term -> term index
    val vocab: Map[String, Int] = vocabArray.view.zipWithIndex.toMap

    /***********************************
      * Feature Extraction: Term Frequency
      ***********************************/

    // Convert documents into term count vectors
    val featureVectors: RDD[(Long, Vector)] =
      tokenized.zipWithIndex.map { case (tokens, id) =>
        val counts = new mutable.HashMap[Int, Double]()
        tokens.foreach { term =>
          if (vocab.contains(term)) {
            val idx = vocab(term)
            counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
          }
        }
        (id, Vectors.sparse(vocab.size, counts.toSeq))
      }


//    /***********************************
//      * Feature Extraction: TF-IDF
//      ***********************************/
//
//    // TF Vectors
//    val featureVectors: RDD[(Long,Vector)] = tokenized.map(TFExtractor.feature(10000,_)).zipWithIndex().map{ case (x,y) => (y,x)};
//
//    featureVectors.collect().foreach(x => println("Feature Vector: " + x))

    // TFIDF Vectors
    //val featureVectors = new TFIDFWholeExtractor(10000).feature(files)

    /***********************************
      * Topic Modelling: Latent Dirichlet Allocation (LDA)
      ***********************************/
    // LDA model
    val numTopics: Integer =  Math.floor(files.count() / 2).toInt
    val ldaModel = LDAAlgorithm.calculate(numTopics,featureVectors)

    // Topics per document
    val docs: Array[String] =  files.collect.map(_._1)
    val topicsDistribution : RDD[(String, Vector)] = ldaModel.topicDistributions.map{ case (key,value) => (docs(key.toInt),value)}


    /***********************************
      * Print Results
      ***********************************/
    val avgLogLikelihood = ldaModel.logLikelihood / featureVectors.count()

    // Print topics, showing top-weighted 10 terms for each topic.
    val topicIndices = ldaModel.describeTopics(maxTermsPerTopic = 10)
    topicIndices.foreach { case (terms, termWeights) =>
      println("TOPIC:")
      terms.zip(termWeights).foreach { case (term, weight) =>
        println(s"${vocabArray(term.toInt)}\t$weight")
      }
      println()
    }


    // Output topics. Each is a distribution over words (matching word count vectors)
//    println("Learnt topics (as distributions over vocab of " + ldaModel.vocabSize + " words):")
//    val topics = ldaModel.topicsMatrix
//    for (topic <- Range(0, numTopics)) {
//      print("Topic " + topic + ":")
//      for (word <- Range(0, ldaModel.vocabSize)) { print(" " + topics(word, topic)); }
//      println()
//    }

    println("Topics per Doc")
    topicsDistribution.collect().foreach{x =>
      val doc = x._1.substring(x._1.indexOf("corpus/"))
      println(s"$doc:\t"+x._2)
    }
    println()
    /***********************************
      * Jensen-Shannon Divergence
      ***********************************/
    println("Distance: ")
    topicsDistribution.collect().foreach(x =>
      topicsDistribution.collect().foreach { y =>
        val doc1 = x._1.substring(x._1.indexOf("corpus/"))
        val doc2 = y._1.substring(y._1.indexOf("corpus/"))
        val distance = JensenShannonDivergence.distance(x._2.toArray, y._2.toArray)
        println(s"$doc1 <-> $doc2:\t $distance")
      }
    )



  }


}
