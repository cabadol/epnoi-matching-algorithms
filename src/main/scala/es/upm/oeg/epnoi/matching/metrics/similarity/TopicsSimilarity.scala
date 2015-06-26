package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.domain.entity.TopicDistribution

/**
 * Created by cbadenes on 22/04/15.
 *
 * Text Similarity measure based on a topic model created from Term-Frequency vectors for each document.
 * Model described by a Latent Dirichlet Allocation (LDA) implemented by a Distributed Expectation-Maximization algorithm
 */
object TopicsSimilarity {

  def apply(t1: TopicDistribution, t2:TopicDistribution): Double={
    JensenShannonSimilarity(t1.distribution,t2.distribution)
  }



}
