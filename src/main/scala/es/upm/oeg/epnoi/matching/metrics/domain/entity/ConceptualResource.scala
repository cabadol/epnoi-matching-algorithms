package es.upm.oeg.epnoi.matching.metrics.domain.entity

import es.upm.oeg.epnoi.matching.metrics.feature.{WordCounter, Concepts}
import org.apache.spark.mllib.linalg.{Vector, Vectors}

/**
 * An extension of resource described by concepts
 * @param resource
 */
case class ConceptualResource (resource: RegularResource) extends Serializable{

  val concepts = resource.words match{
    case None => None
    case Some(words) => Some(Concepts(words))
  }

  def bagOfConcepts = concepts match{
    case None => Seq.empty
    case Some(w) => w
  }

  /**
   * Frequency Vector
   * @param vocabulary
   * @return
   */
  def featureVector (vocabulary: Vocabulary): Vector = concepts match{
    case None => Vectors.sparse(vocabulary.size, Seq.empty)
    case Some(concepts) => WordCounter.count(concepts, vocabulary)
  }

}
