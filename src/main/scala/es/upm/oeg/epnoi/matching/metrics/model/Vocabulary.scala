package es.upm.oeg.epnoi.matching.metrics.model

import org.apache.spark.rdd.RDD

/**
 * Terms used in the corpus
 * @param corpus
 */
class Vocabulary (corpus: RDD[Resource]) extends Serializable{

  // Map term -> term index
  // TODO avoid this inserting words in external resource, e.g Elasticsearch
  private val map: collection.Map[String, Long] = corpus.flatMap(x=> x.terms).distinct.zipWithIndex.collectAsMap

  val size = map.size

  /**
   * Is term defined in vocabulary?
   * @param term
   * @return
   */
  def contains (term: String): Boolean ={
    map.contains(term)
  }

  /**
   * internal key of the term
   * @param term
   * @return
   */
  def key(term: String): Long ={
    map(term)
  }

}
