package es.upm.oeg.epnoi.matching.metrics.model

/**
 * An entity primarily responsible for making the resource.
 * @param uri
 * @param name
 * @param surname
 */
case class Author (uri: String, name: String, surname: String) extends Serializable{

  var topicModel: Array[Double] = Array.empty

  // Orcid, dblp, ads
  var digitalIds: Map[String,String] = Map.empty

}
