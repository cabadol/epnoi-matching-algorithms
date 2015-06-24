package es.upm.oeg.epnoi.matching.metrics.domain.entity

import es.upm.oeg.epnoi.matching.metrics.feature.{WordCounter, Concepts}
import org.apache.spark.mllib.linalg.{Vector, Vectors}

/**
 * An extension of resource described by concepts
 * @param resource
 */
case class ConceptualResource (resource: RegularResource) extends Serializable{

  def bagOfConcepts = resource.bagOfWords //actually not conceptualization

  /**
   * Frequency Vector
   * @param vocabulary
   * @return
   */
  def featureVector (vocabulary: Vocabulary): Vector = bagOfConcepts match{
    case Seq() => Vectors.sparse(vocabulary.size, Seq.empty)
    case concepts => WordCounter.count(concepts, vocabulary)
  }

}
