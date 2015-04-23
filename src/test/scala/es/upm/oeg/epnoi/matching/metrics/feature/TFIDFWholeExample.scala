package es.upm.oeg.epnoi.matching.metrics.feature

import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by cbadenes on 20/04/15.
 */
object TFIDFWholeExample {

  def main(args: Array[String]): Unit = {

    // Load a directory
    val files: RDD[(String,String)]= SparkWrapper.readCorpus("src/test/resources/tfidf")

    // tf-idf
    val tfidfVectors = new TFIDFCounter(1000).feature(files)


  }

}
