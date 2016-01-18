package es.upm.oeg.epnoi.matching.metrics.domain.entity

/**
 * An entity primarily responsible for making the resource.
 * @param uri (uniform resource identifier) string of characters used to identify the metadata
 * @param name
 * @param surname
 */
case class Author(uri: String, name: String, surname: String) extends Serializable {

  var digitalId: DigitalID = null;

  override def equals(o: Any) = o match {
    case that: Author => that.uri.equalsIgnoreCase(this.uri)
    case _ => false
  }

  override def hashCode = uri.toLowerCase().hashCode

}
