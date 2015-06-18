package es.upm.oeg.epnoi.matching.metrics.domain.entity

/**
 * Digital signature of an author
 * @param orcid
 * @param dblpId
 * @param adsId
 */
case class DigitalID (orcid: Option[String], dblpId: Option[String], adsId: Option[String]) extends Serializable{

}
