package es.upm.oeg.epnoi.matching.metrics.space

import es.upm.oeg.epnoi.matching.metrics.feature.WordCounter
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD

/**
 * Collection of resources described by words
 * @param resources
 */
case class WordSpace (resources: RDD[Resource]) extends Serializable {

  // List of words
  val vocabulary = new Vocabulary(resources.flatMap(_.bagOfWords))

  // List of resources indexed
  val indexes = resources.zipWithIndex.map{case (value,key) => (key,value)}

  // Term-Frequency Vectors using vocabulary as index
  def featureVectors = indexes.map{case (key,resource)=>
    val counter: Vector = resource.words match{
      case None => Vectors.sparse(vocabulary.size, Seq.empty)
      case Some(terms) => WordCounter.count(terms, vocabulary) // Term-Frequency Feature Vector
    }
    (key, counter)
  }

}
