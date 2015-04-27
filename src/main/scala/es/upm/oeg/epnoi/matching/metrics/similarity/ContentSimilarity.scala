package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.model._

/**
 * Text Similarity measure based on a topic model created from Term-Frequency vectors for each document.
 * Model described by a Latent Dirichlet Allocation (LDA) implemented by a Distributed Expectation-Maximization algorithm
 */
object ContentSimilarity {


  def between(r1: Resource, r2:Resource): Double={
    (r1.topicModel,r2.topicModel) match{
      case (None,None) | (None,Some(_)) | (Some(_),None) => throw new IllegalArgumentException("No topic model defined in resource")
      case (Some(tm1),Some(tm2)) => JensenShannonSimilarity.between(tm1,tm2)
    }

  }

}
