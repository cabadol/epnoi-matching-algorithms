package es.upm.oeg.epnoi.matching.metrics.space

import es.upm.oeg.epnoi.matching.metrics.feature.Concepts

/**
 * An extended resource described by terms
 * @param resource
 */
case class ConceptualResource (resource: Resource) extends Serializable{

  val terms = resource.words match{
    case None => None
    case Some(words) => Some(Concepts(words))
  }

  def bagOfTerms = terms match{
    case None => Seq.empty
    case Some(w) => w
  }

}
