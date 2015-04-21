package es.upm.oeg.epnoi.matching.metrics.topics

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.clustering.LDA
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by cbadenes on 20/04/15.
 */
object LDAExample {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("LDA Example")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    // Load and parse the data
    val data = sc.textFile("src/test/resources/topic-corpus/test1.txt")
    val parsedData = data.map(s => Vectors.dense(s.trim.split(' ').map(_.toDouble)))
    // Index documents with unique IDs
    val corpus = parsedData.zipWithIndex.map(_.swap).cache()

    // Cluster the documents into three topics using LDA
    val ldaModel = new LDA().setK(3).run(corpus)

    // Output topics. Each is a distribution over words (matching word count vectors)
    println("Learned topics (as distributions over vocab of " + ldaModel.vocabSize + " words):")
    val topics = ldaModel.topicsMatrix
    for (topic <- Range(0, 3)) {
      print("Topic " + topic + ":")
      for (word <- Range(0, ldaModel.vocabSize)) { print(" " + topics(word, topic)); }
      println()
    }

    // Topics per document
    val topicsPerDoc = ldaModel.topicDistributions.collect()
    println("Topics per Doc");
    topicsPerDoc foreach { dist =>
      println ("Doc distribution: " + dist)
    }



  }

}
