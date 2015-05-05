package es.upm.oeg.epnoi.matching.metrics.domain

/**
 * An entity primarily responsible for making the resource.
 * @param uri (uniform resource identifier) string of characters used to identify the metadata
 * @param name
 * @param surname
 * @param digitalId orcid, dblp, ads
 */
case class Author (uri: String, name: String, surname: String, digitalId: Option[DigitalID]) extends Serializable{
}
