package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.distance.JensenShannonDivergence

/**
 * Created by cbadenes on 22/04/15.
 */
object JensenShannonSimilarity {

  def apply(p: Array[Double], q: Array[Double]): Double = {
    Math.pow(10,-JensenShannonDivergence(p,q))
  }

}
