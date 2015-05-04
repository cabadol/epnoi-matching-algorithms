package es.upm.oeg.epnoi.matching.metrics.space

/**
 * Density function about topics
 * @param data
 */
// Warning: Case Class, equals and hashcode using Array not running
case class TopicDistribution (data: collection.mutable.WrappedArray[Double]) {

  def distribution = data.array


}
