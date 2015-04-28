package es.upm.oeg.epnoi.matching.metrics.model.item

/**
 *
 * @param orcid
 * @param dblpId
 * @param adsId
 */
case class DigitalID (orcid: Option[String], dblpId: Option[String], adsId: Option[String]) extends Serializable{

}
