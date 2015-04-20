package es.upm.oeg.epnoi.matching.algorithms

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.feature.{IDF, HashingTF}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by cbadenes on 20/04/15.
 */
object MultiLineExample {

  def main(args: Array[String]): Unit = {
    /***********************************
      * Spark Context
      ***********************************/
    println("Starting...")
    val conf = new SparkConf().setMaster("local").setAppName("TFIDF Example")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)


    /***********************************
      * Feature Extraction: TF-IDF
      ***********************************/
    // Load a file
    val lines: RDD[String] = sc.textFile("src/test/resources/tfidf/test1.txt")

    // Print lines
    lines.foreach(x => println("Lines Element => '" + x + "'"))

    // Split lines into words
    val words: RDD[Array[String]] = lines.map(x => x.split(" "))

    words.foreach(x => println("Words Element => '" + x + "'"))

  }


}
