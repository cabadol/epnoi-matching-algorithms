package es.upm.oeg.epnoi.matching.metrics.model.item

/**
 * Information about a resource
 * @param title A name given to the resource.
 * @param published A point or period of time associated with the publication of the resource.
 * @param authors List of @Author
 */
case class Metadata (title: String, published: String, authors: Option[Seq[Author]]) extends Serializable{

  //TODO license, format, ....
}
