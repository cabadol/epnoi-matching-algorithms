package es.upm.oeg.epnoi.matching.metrics.utils

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by cbadenes on 22/04/15.
 */
object SparkWrapper {

  val conf = new SparkConf().setMaster("local").setAppName("Local Spark Example")

  val sc = new SparkContext(conf)

  Logger.getRootLogger.setLevel(Level.WARN)

  def readCorpus (directory: String): RDD[(String,String)] = {
    sc.wholeTextFiles(directory)
//      .map{case (file,content) =>
//      // File name only (path removed)
//      (file.substring(file.lastIndexOf("/")+1),content)
//    }
  }

}
