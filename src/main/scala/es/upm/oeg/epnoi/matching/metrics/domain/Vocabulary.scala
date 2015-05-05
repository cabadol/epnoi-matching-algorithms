package es.upm.oeg.epnoi.matching.metrics.domain

import org.apache.spark.rdd.RDD

/**
 * Unique list of words
 * @param tokens
 */
class Vocabulary (tokens: RDD[String]) extends Serializable{

  val words = tokens.distinct

  // Map term -> term index
  private val wordsIndex: collection.Map[String, Long] = words.zipWithIndex.collectAsMap()

  val size = wordsIndex.size


  /**
   * Is term defined in vocabulary?
   * @param word
   * @return
   */
  def contains (word: String): Boolean ={
    wordsIndex.contains(word)
  }

  /**
   * key of the word in the vocabulary
   * @param word
   * @return
   */
  def key(word: String): Long ={
    wordsIndex(word)
  }

}
