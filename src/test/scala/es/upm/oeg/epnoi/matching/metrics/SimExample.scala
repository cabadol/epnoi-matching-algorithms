package es.upm.oeg.epnoi.matching.metrics

import es.upm.oeg.epnoi.matching.metrics.feature.{Tokenizer, Vocabulary, WordCounter}
import es.upm.oeg.epnoi.matching.metrics.topics.LDAAlgorithm
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection
import scala.collection.mutable

/**
 * Created by cbadenes on 20/04/15.
 */
object SimExample {

  def main(args: Array[String]): Unit = {

    // Initialize Spark Context
    val conf = new SparkConf().setMaster("local").setAppName("Content Similarity Example")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)

    // Read corpus
    val files: RDD[(String,String)]= sc.wholeTextFiles("src/test/resources/topic-corpus/*.txt")
    val corpus: RDD[String] = files.map(_._2)



    // Normalize in tokens
    val tokenized : RDD[Seq[String]] = Tokenizer.split(corpus)

    // Count words
    val termCounts: Array[(String, Long)] = WordCounter.flatCount(tokenized)

    // Vocabulary from counted words: filtered or not
    val vocabulary: Array[String] = Vocabulary.lessCommon(25,termCounts)

    // Map term -> term index
    val vocab: Map[String, Int] = vocabulary.view.zipWithIndex.toMap


    // Documents correlation
    val documents: collection.Map[String, Long] = files.map(_._1).zipWithIndex().collectAsMap()



    // Count terms that appear in the vocabulary
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

    // Latent Dirichlet Allocation (LDA) model - Expectation-MAximization Algorithm
    val ldaModel = LDAAlgorithm.calculate(numTopics=4,featureVectors)

    // Topics per document
    val docs: Array[String] =  files.collect.map(_._1)
    val topicsDistribution : RDD[(String, Vector)] = ldaModel.topicDistributions.map{ case (key,value) => (docs(key.toInt),value)}


    /***********************************
      * Print Topics
      ***********************************/
    // Print topics, showing top-weighted 10 terms for each topic.
    println("*"*20+" Topics:")
    println("Average LogLikelihood: "+ ldaModel.logLikelihood / featureVectors.count())
    val topicIndices = ldaModel.describeTopics(maxTermsPerTopic = 10)
    topicIndices.foreach { case (terms, termWeights) =>
      println("TOPIC:")
      terms.zip(termWeights).foreach { case (term, weight) =>
        println(s" ·${vocabulary(term.toInt)}\t$weight")
      }
      println()
    }

    /***********************************
      * Print Topics in Documents
      ***********************************/
    println("*"*20+" Topics per Document:")
    topicsDistribution.collect().foreach{x =>
      val doc = x._1.substring(x._1.indexOf("corpus/"))
      println(s"$doc:\t"+x._2)
    }
    println()
    /***********************************
      * Jensen-Shannon Divergence
      ***********************************/
    println("*"*20+" Distance between documents:")
    topicsDistribution.collect().foreach(x =>
      topicsDistribution.collect().foreach { y =>
        val doc1 = x._1.substring(x._1.indexOf("corpus/"))
        val doc2 = y._1.substring(y._1.indexOf("corpus/"))
        val similarity = Similarity.jensenShannon(x._2.toArray, y._2.toArray)
        print(f"$similarity%1.5f")
        println(s"\t$doc1 <-> $doc2")
      }
    )



  }


}
