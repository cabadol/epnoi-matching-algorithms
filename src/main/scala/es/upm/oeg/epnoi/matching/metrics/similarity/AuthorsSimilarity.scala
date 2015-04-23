package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.model.Author

/**
 * Created by cbadenes on 23/04/15.
 */
class AuthorsSimilarity {


  def between(a1: Author, a2: Author): Unit ={
    JensenShannonSimilarity.between(a1.topicModel, a2.topicModel)
  }

}
