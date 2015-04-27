package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.model._
import org.apache.spark.rdd.RDD

/**
 * Similarity metric for Research Objects
 */
object ROSimilarity {

  /**
   * Research Object Similarity
   * @param r1
   * @param r2
   * @return
   */
  def between (r1: Resource, r2: Resource): Double={
    // First approach: Only textual similarity
    ContentSimilarity.between(r1,r2)
  }


}
