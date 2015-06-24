package es.upm.oeg.epnoi.matching.metrics.domain.space

import es.upm.oeg.epnoi.matching.metrics.domain.entity.{RegularResource, Vocabulary}
import es.upm.oeg.epnoi.matching.metrics.feature.WordCounter
import es.upm.oeg.epnoi.matching.metrics.similarity.EuclideanSimilarity
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD

/**
 * Collection of resources described by words
 * @param resources
 */
case class WordsSpace (resources: RDD[RegularResource]) extends Serializable {

  // List of words
  val vocabulary = new Vocabulary(resources.flatMap(_.bagOfWords))

  // List of resources indexed
  val indexes = resources.zipWithIndex.map{case (value,key) => (key,value)}

  // Word-Frequency Vectors using vocabulary as index
  def frequencyVector(r:RegularResource) : Vector={
    r.bagOfWords match{
      case Seq() => Vectors.sparse(vocabulary.size, Seq.empty)
      case terms => WordCounter.count(terms, vocabulary)
    }
  }

  // Word-Frequency vectors indexed
  def featureVectors = indexes.map{case (key,resource)=> (key, frequencyVector(resource))}

  /**
   * Similarity between two resources based on the Euclidean distance of word-frequency vectors
   * @param r1
   * @param r2
   * @return
   */
  def similarity(r1: RegularResource, r2: RegularResource): Double ={
      EuclideanSimilarity(frequencyVector(r1),frequencyVector(r2))
  }

}
