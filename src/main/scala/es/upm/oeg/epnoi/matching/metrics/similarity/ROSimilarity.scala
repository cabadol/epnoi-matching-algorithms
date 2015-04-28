package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.model._
import es.upm.oeg.epnoi.matching.metrics.model.item.SemanticResource
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
  def apply (r1: SemanticResource, r2: SemanticResource): Double={

    // First approach: Only textual similarity
    TopicsSimilarity.between(r1,r2)
  }


}
