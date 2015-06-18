package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.domain.entity.{Vocabulary, ConceptualResource, RegularResource}

/**
 * Created by cbadenes on 05/06/15.
 */
object ContentSimilarity {

  def apply(vocab: Vocabulary, r1: RegularResource, r2: RegularResource): Double = {
    CosineSimilarity(r1.featureVector(vocab).toArray,r2.featureVector(vocab).toArray)
  }

  def apply(vocab: Vocabulary, r1: ConceptualResource, r2: ConceptualResource): Double = {
    CosineSimilarity(r1.featureVector(vocab).toArray,r2.featureVector(vocab).toArray)
  }


}
