package es.upm.oeg.epnoi.matching.metrics.domain.entity

import es.upm.oeg.epnoi.matching.metrics.feature.WordCounter
import org.apache.spark.mllib.linalg.{Vectors, Vector}

/**
 * Relevant data to interpreting and preserving the results of scientific investigations (e.g. publications,
 * source code, data artefacts, methods, experiments)
 * @param uri (uniform resource identifier) string of characters used to identify a resource
 * @param url (uniform resource locator) location of the resource on the system
 * @param metadata
 * @param resources
 */
case class RegularResource (uri: String, url: String, metadata: Metadata, bagOfWords: Seq[String], resources: Seq[RegularResource]) extends Serializable{

  /**
   * Feature Vector as Frequency Vector. When aggregated resources, it is the vectorial sum of the aggregated feature vectors
   * @param vocabulary
   * @return
   */
  def featureVector (vocabulary: Vocabulary): Vector = resources match{
    case Seq() => partialFeatureVector(vocabulary)
    case resources => resources.map(x=>x.featureVector(vocabulary)).reduce((v1,v2)=>v1) // nested vectorial sum
  }

  private def partialFeatureVector (vocabulary: Vocabulary): Vector = bagOfWords match{
    case Seq() => Vectors.sparse(vocabulary.size, Seq.empty)
    case words => WordCounter.count(words, vocabulary)
  }


}
