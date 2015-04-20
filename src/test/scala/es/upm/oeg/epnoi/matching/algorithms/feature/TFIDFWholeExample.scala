package es.upm.oeg.epnoi.matching.algorithms.feature

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by cbadenes on 20/04/15.
 */
object TFIDFWholeExample {

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
    val files: RDD[(String,String)]= sc.wholeTextFiles("src/test/resources/tfidf")

    /***********************************
      * Feature Extraction: TF-IDF
      ***********************************/
    val tfidfVectors = new TFIDFWholeExtractor(1000).feature(files)


  }

}
