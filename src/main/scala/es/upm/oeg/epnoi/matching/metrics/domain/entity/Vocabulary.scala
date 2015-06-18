package es.upm.oeg.epnoi.matching.metrics.domain.entity

import org.apache.spark.rdd.RDD

/**
 * Unique list of words
 * @param tokens
 */
class Vocabulary (tokens: RDD[String]) extends Serializable{

  val words = tokens.distinct

  // Map [term] -> [term ,index]
  private val wordsMap: collection.Map[String, Long] = words.zipWithIndex.collectAsMap()

  val size = wordsMap.size


  /**
   * Is term defined in vocabulary?
   * @param word
   * @return
   */
  def contains (word: String): Boolean ={
    wordsMap.contains(word)
  }

  /**
   * key of the word in the vocabulary
   * @param word
   * @return
   */
  def key(word: String): Long ={
    wordsMap(word)
  }

}
