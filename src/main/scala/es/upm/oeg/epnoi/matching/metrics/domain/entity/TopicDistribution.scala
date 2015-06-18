package es.upm.oeg.epnoi.matching.metrics.domain.entity

import es.upm.oeg.epnoi.matching.metrics.aggregation.Bernoulli

/**
 * Density function about topics
 * @param data
 */
// Warning: Case Class, equals and hashcode using Array not running
case class TopicDistribution (data: collection.mutable.WrappedArray[Double]) {

  def distribution = data.array

  def +(topicDistribution: TopicDistribution): TopicDistribution ={
    TopicDistribution(Bernoulli(distribution,topicDistribution.distribution))
  }

}
