package es.upm.oeg.epnoi.matching.metrics.model.item

/**
 *
 * @param resource
 * @param topics
 */
case class SemanticResource (resource: Resource, terms: Option[Seq[String]], topics: TopicDistribution) extends Serializable {


}
