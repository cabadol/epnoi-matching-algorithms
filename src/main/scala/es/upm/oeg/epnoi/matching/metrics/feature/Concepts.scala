package es.upm.oeg.epnoi.matching.metrics.feature

import org.apache.spark.rdd.RDD

/**
 * Create concepts from words
 */
object Concepts {

  def apply(words: RDD[String]): RDD[String]={
    words
  }

  def apply(words: Seq[String]): Seq[String]={
    words
  }

}
