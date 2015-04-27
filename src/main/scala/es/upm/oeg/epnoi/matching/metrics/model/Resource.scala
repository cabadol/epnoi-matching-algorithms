package es.upm.oeg.epnoi.matching.metrics.model

/**
 * Relevant data to interpreting and preserving the results of scientific investigations (e.g. publications,
 * source code, data artefacts, methods, experiments)
 * @param uri
 * @param metadata
 * @param terms
 * @param topicModel
 * @param resources
 */
case class Resource (uri: String, metadata: Metadata, terms: Seq[String], topicModel: Option[Array[Double]], resources: Option[List[Resource]]) extends Serializable{

  if (terms.isEmpty) throw new IllegalArgumentException("terms can not be empty")
}
