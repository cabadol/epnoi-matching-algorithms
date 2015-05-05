package es.upm.oeg.epnoi.matching.metrics.domain

/**
 * A conceptual resource described by a distribution of topics
 * @param conceptualResource
 * @param topics
 */
case class SemanticResource (conceptualResource: ConceptualResource, topics: TopicDistribution) extends Serializable {

}
