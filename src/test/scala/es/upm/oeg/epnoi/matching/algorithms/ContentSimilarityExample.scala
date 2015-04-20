package es.upm.oeg.epnoi.matching.algorithms

import es.upm.oeg.epnoi.matching.algorithms.feature.{TFExtractor, TFIDFWholeExtractor}
import es.upm.oeg.epnoi.matching.algorithms.topics.LDAAlgorithm
import es.upm.oeg.epnoi.matching.metric.JensenShannonDivergence
import org.apache.log4j.{Level, Logger}
import org.apache.mahout.common.distance.ManhattanDistanceMeasure
import org.apache.spark.mllib.clustering.LDA
import org.apache.spark.mllib.feature.{IDF, HashingTF}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by cbadenes on 20/04/15.
 */
object ContentSimilarityExample {

  def main(args: Array[String]): Unit = {

    /***********************************
      * Spark Context
      ***********************************/
    println("Starting...")
    val conf = new SparkConf().setMaster("local").setAppName("TFIDF Whole Example")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)


    /***********************************
      * Load corpus
      ***********************************/
    // Load a directory
    val files: RDD[(String,String)]= sc.wholeTextFiles("src/test/resources/wiki-corpus/*.txt")

    /***********************************
      * Feature Extraction: TF-IDF
      ***********************************/
    // TF Vectors
    val featureVectors = files.mapValues(x => TFExtractor.feature(10000,x));

    // TFIDF Vectors
    //val featureVectors = new TFIDFWholeExtractor(10000).feature(files)

    /***********************************
      * Topic Modelling: Latent Dirichlet Allocation (LDA)
      ***********************************/
    // LDA model
    val numTopics = 2
    val ldaModel = LDAAlgorithm.calculate(numTopics,featureVectors)

    // Topics per document
    val docs = featureVectors.keys.collect()
    val topicsDistribution : RDD[(String, Vector)] = ldaModel.topicDistributions.map{ case (key,value) => (docs(key.toInt),value)}


    /***********************************
      * Print Results
      ***********************************/
    val avgLogLikelihood = ldaModel.logLikelihood / featureVectors.count()

    // Output topics. Each is a distribution over words (matching word count vectors)
    println("Learnt topics (as distributions over vocab of " + ldaModel.vocabSize + " words):")
    val topics = ldaModel.topicsMatrix
    for (topic <- Range(0, numTopics)) {
      print("Topic " + topic + ":")
      for (word <- Range(0, ldaModel.vocabSize)) { print(" " + topics(word, topic)); }
      println()
    }

    println("Topics per Doc")
    topicsDistribution.collect().foreach(x => println("Element: "+ x))

    /***********************************
      * Jensen-Shannon Divergence
      ***********************************/
    topicsDistribution.collect().foreach(x =>
      topicsDistribution.collect().foreach { y =>
        val doc1 = x._1.substring(x._1.indexOf("corpus/"))
        val doc2 = y._1.substring(y._1.indexOf("corpus/"))
        val distance = JensenShannonDivergence.distance(x._2.toArray, y._2.toArray)
        println("JSD [" + doc1 + "|" + doc2 + "]: \t" + distance)
      }
    )



  }


}
