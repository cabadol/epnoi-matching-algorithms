package es.upm.oeg.epnoi.matching.metrics

import es.upm.oeg.epnoi.matching.metrics.feature.{LuceneTokenizer, SimpleTokenizer, Vocabulary, WordCounter}
import es.upm.oeg.epnoi.matching.metrics.topics.LDAAlgorithm
import org.apache.log4j.{Level, Logger}
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.spark.mllib.clustering.LDA
import org.apache.spark.mllib.feature.HashingTF
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
    val corpus: RDD[(String,String)]= sc.wholeTextFiles("src/test/corpus/articles/**").map{case (file,content) =>
      (file.substring(file.lastIndexOf("/")+1),content)
    }

    // Extract tokens
    // -> Simple Tokenizer
    // val tokenized: RDD[(String, Seq[String])] =  corpus.mapValues(SimpleTokenizer.split)
    // -> Lucene Tokenizer
    val tokenized: RDD[(String, Seq[String])] =  corpus.mapValues(LuceneTokenizer.split)

    println("*"*20+" Corpus:")
    tokenized.collect.foreach(x=>println(s"$x"))

    // Count words
    val termCounts: Array[(String, Long)] = WordCounter.flatCount(tokenized.values)
//
    // Global Vocabulary
    val vocabulary: Array[String] = Vocabulary.all(termCounts)
//
    // Map term -> term index
    val vocab: Map[String, Int] = vocabulary.zipWithIndex.toMap


    // Map document to index
    val docToKey: collection.Map[String,Long] = corpus.map(_._1).zipWithIndex.collectAsMap()
    val keyToDoc: collection.Map[Long,String] = docToKey.map{case (doc,key) => (key,doc)}

    // Feature Vectors by Term-Frequency Spark module
//    val tf = new HashingTF(1000000)
//    val featureVectors: RDD[(Long, Vector)] = tokenized.map{ case (doc,tokens) => (docToKey(doc), tf.transform(tokens))}


    // Feature Vectors by Word Counter
    // Count terms that appear in the vocabulary
    val featureVectors: RDD[(Long, Vector)] =
      tokenized.map { case (doc, tokens) =>
        val counts = new mutable.HashMap[Int, Double]()
        tokens.foreach { term =>
          if (vocab.contains(term)) {
            val idx = vocab(term)
            counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
          }
        }
        (docToKey(doc), Vectors.sparse(vocab.size, counts.toSeq))
      }


    println("creating LDA model...")
    // Latent Dirichlet Allocation (LDA) model - Expectation-MAximization Algorithm
    val ldaModel = new LDA().setK(4).setMaxIterations(60).run(featureVectors)

    // Topics per document
    val topicsDistribution : RDD[(String, Vector)] = ldaModel.topicDistributions.map{ case (key,value) => (keyToDoc(key.toInt),value)}


    /***********************************
      * Print Topics
      ***********************************/
    // Print topics, showing top-weighted 10 terms for each topic.
    println("*"*20+" Topics:")
    println("LogLikelihood: "+ ldaModel.logLikelihood)
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
      println(x._1 +":\t"+x._2)
    }
    println()

    /***********************************
      * Jensen-Shannon Divergence
      ***********************************/
    var simMatrix: collection.Map[String, List[(String,Double)]] = Map()

    topicsDistribution.collect().foreach { x =>
      var simVector: List[(String,Double)] = List()
      topicsDistribution.collect().foreach { y =>
        simVector = (y._1,Similarity.jensenShannon(x._2.toArray, y._2.toArray)) :: simVector
      }
      simMatrix = simMatrix.+(x._1 -> simVector)
    }

    println("*"*20+" Similarity between documents:")
    for (pairs <- simMatrix){
      println("\t·"+pairs._1)
      pairs._2.sortBy(_._2).foreach{ x =>
        print("\t\t")
        val sim = x._2
        print(f"$sim%1.5f")
        println(":\t"+x._1)
      }
    }



  }


}
