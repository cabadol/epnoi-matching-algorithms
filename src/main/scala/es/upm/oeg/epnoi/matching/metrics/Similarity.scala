package es.upm.oeg.epnoi.matching.metrics

/**
 * Created by cbadenes on 21/04/15.
 */
object Similarity {

  def jensenShannon(p: Array[Double], q: Array[Double]): Double = {
    Math.pow(10,-Distance.jensenShannon(p,q))
  }

}
