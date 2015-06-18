package es.upm.oeg.epnoi.matching.metrics.domain.entity

import es.upm.oeg.epnoi.matching.metrics.feature.WordCounter
import org.apache.spark.mllib.linalg.{Vectors, Vector}

/**
 * Relevant data to interpreting and preserving the results of scientific investigations (e.g. publications,
 * source code, data artefacts, methods, experiments)
 * @param uri (uniform resource identifier) string of characters used to identify a resource
 * @param url (uniform resource locator) location of the resource on the system
 * @param metadata
 * @param words
 * @param resources
 */
case class RegularResource (uri: String, url: String, metadata: Metadata, words: Option[Seq[String]], resources: Option[Seq[RegularResource]]) extends Serializable{

  val bagOfWords = words match{
    case None => Seq.empty
    case Some(w) => w
  }

  /**
   * Feature Vector as Frequency Vector. When aggregated resources, it is the vectorial sum of the aggregated feature vectors
   * @param vocabulary
   * @return
   */
  def featureVector (vocabulary: Vocabulary): Vector = resources match{
    case None => partialFeatureVector(vocabulary)
    case Some(resources) => resources.map(x=>x.featureVector(vocabulary)).reduce((v1,v2)=>v1) // nested vectorial sum
  }

  private def partialFeatureVector (vocabulary: Vocabulary): Vector = words match{
    case None => Vectors.sparse(vocabulary.size, Seq.empty)
    case Some(words) => WordCounter.count(words, vocabulary)
  }


}
