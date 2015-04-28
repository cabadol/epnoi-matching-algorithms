package es.upm.oeg.epnoi.matching.metrics.model.item

import org.apache.spark.rdd.RDD

/**
 *
 * @param resource
 * @param topics
 */
case class SemanticResource (resource: Resource, terms: Option[Seq[String]], topics: TopicDistribution) extends Serializable {

}
