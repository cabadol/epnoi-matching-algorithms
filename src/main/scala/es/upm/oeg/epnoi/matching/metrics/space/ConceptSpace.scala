package es.upm.oeg.epnoi.matching.metrics.space

import es.upm.oeg.epnoi.matching.metrics.feature.WordCounter
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD

/**
 * Collection of conceptual resources, i.e. resources described by terms.
 * - vocabulary: list of unique terms used by conceptual resources
 * - indexes: key map for conceptual resources
 * - feature vectors: term-frequency vectors from conceptual resources and indexes
 * @param conceptualResources
 */
case class ConceptSpace (conceptualResources: RDD[ConceptualResource]) extends Serializable {

  // List of concepts
  val vocabulary = new Vocabulary(conceptualResources.flatMap(_.bagOfConcepts))

  // List of conceptual resources indexed
  val indexes = conceptualResources.zipWithIndex.map{case (value,key) => (key,value)}

  // Term-Frequency Vectors using vocabulary as index
  def featureVectors = indexes.map{case (key,conceptualResource)=>
    val counter: Vector = conceptualResource.concepts match{
      case None => Vectors.sparse(vocabulary.size, Seq.empty)
      case Some(terms) => WordCounter.count(terms, vocabulary)
    }
    (key, counter)
  }

}
