package es.upm.oeg.epnoi.matching.metrics.model

/**
 * Information about a resource
 * @param uri (uniform resource identifier) string of characters used to identify a resource
 * @param url (uniform resource locator) location of the resource on the system
 * @param title A name given to the resource.
 * @param published A point or period of time associated with the publication of the resource.
 * @param authors List of @Author
 */
case class Metadata (uri: String, url: String, title: String, published: String, authors: List[Author]) extends Serializable{

}
