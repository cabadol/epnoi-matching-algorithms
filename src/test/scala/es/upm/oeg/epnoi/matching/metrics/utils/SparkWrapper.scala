package es.upm.oeg.epnoi.matching.metrics.utils

import java.lang.management.{ManagementFactory, RuntimeMXBean}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by cbadenes on 22/04/15.
 */
object SparkWrapper {


  println("Runtime max memory: " + Runtime.getRuntime.maxMemory())
  println("Runtime free memory: " + Runtime.getRuntime.freeMemory())


  val conf = new SparkConf().
    setMaster("local").
    setAppName("Local Spark Example").
    set("spark.executor.memory", "6g")

  val sc = new SparkContext(conf)


  //Logger.getRootLogger.setLevel(Level.INFO)



  def readCorpus (directory: String): RDD[(String,String)] = {
    sc.wholeTextFiles(directory)
//      .map{case (file,content) =>
//      // File name only (path removed)
//      (file.substring(file.lastIndexOf("/")+1),content)
//    }
  }

}
