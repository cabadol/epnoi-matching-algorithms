package es.upm.oeg.epnoi.matching.metrics.model.item

import org.apache.spark.rdd.RDD

/**
 * Words used in the corpus
 * @param bagOfWords
 */
class Vocabulary (bagOfWords: RDD[String]) extends Serializable{

  val words = bagOfWords.distinct

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
