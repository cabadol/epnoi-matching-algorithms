package es.upm.oeg.epnoi.matching.metrics.domain.entity

/**
 * A conceptual resource described by a distribution of topics
 * @param conceptualResource
 * @param topics
 */
case class TopicalResource (conceptualResource: ConceptualResource, topics: TopicDistribution) extends Serializable {

}
