package es.upm.oeg.epnoi.matching.metrics.domain

import es.upm.oeg.epnoi.matching.metrics.feature.Concepts

/**
 * An extension of resource described by concepts
 * @param resource
 */
case class ConceptualResource (resource: Resource) extends Serializable{

  val concepts = resource.words match{
    case None => None
    case Some(words) => Some(Concepts(words))
  }

  def bagOfConcepts = concepts match{
    case None => Seq.empty
    case Some(w) => w
  }

}