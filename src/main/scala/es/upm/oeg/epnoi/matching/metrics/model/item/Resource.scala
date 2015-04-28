package es.upm.oeg.epnoi.matching.metrics.model.item

/**
 * Relevant data to interpreting and preserving the results of scientific investigations (e.g. publications,
 * source code, data artefacts, methods, experiments)
 * @param uri (uniform resource identifier) string of characters used to identify a resource
 * @param url (uniform resource locator) location of the resource on the system
 * @param metadata
 * @param words
 * @param resources
 */
case class Resource (uri: String, url: String, metadata: Metadata, words: Option[Seq[String]], resources: Option[Seq[Resource]]) extends Serializable{

}
