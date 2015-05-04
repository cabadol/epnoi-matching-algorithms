package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.model._
import es.upm.oeg.epnoi.matching.metrics.space.SemanticResource

/**
 * Text Similarity measure based on a topic model created from Term-Frequency vectors for each document.
 * Model described by a Latent Dirichlet Allocation (LDA) implemented by a Distributed Expectation-Maximization algorithm
 */
object TopicsSimilarity {


  def apply(r1: SemanticResource, r2:SemanticResource): Double={
    JensenShannonSimilarity(r1.topics.distribution,r2.topics.distribution)
  }

}
