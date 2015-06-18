package es.upm.oeg.epnoi.matching.metrics.domain.space

import es.upm.oeg.epnoi.matching.metrics.domain.entity.{ConceptualResource, Vocabulary}
import es.upm.oeg.epnoi.matching.metrics.feature.WordCounter
import es.upm.oeg.epnoi.matching.metrics.similarity.{ContentSimilarity, EuclideanSimilarity}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD

/**
 * Collection of conceptual resources, i.e. resources described by terms.
 * - vocabulary: list of unique terms used by conceptual resources
 * - indexes: key map for conceptual resources
 * - feature vectors: term-frequency vectors from conceptual resources and indexes
 * @param conceptualResources
 */
case class ConceptsSpace (conceptualResources: RDD[ConceptualResource]) extends Serializable {

  // List of concepts
  val vocabulary = new Vocabulary(conceptualResources.flatMap(_.bagOfConcepts))

  // Indexed list of conceptual resources
  val conceptualResourcesMap = conceptualResources.zipWithIndex.map{case (value,key) => (key,value)}

  // Concept-Frequency vectors indexed
  def featureVectors = conceptualResourcesMap.map{case (key,conceptualResource)=> (key, conceptualResource.featureVector(vocabulary))}

  /**
   * Similarity between two conceptual resources based on the Euclidean distance of concept-frequency vectors
   * @param cr1
   * @param cr2
   * @return
   */
  def similarity(cr1: ConceptualResource, cr2: ConceptualResource): Double ={
    ContentSimilarity(vocabulary,cr1,cr2)
  }



}
