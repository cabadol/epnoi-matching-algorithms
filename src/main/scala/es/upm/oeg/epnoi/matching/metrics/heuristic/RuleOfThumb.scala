package es.upm.oeg.epnoi.matching.metrics.heuristic

/**
 * Created by cbadenes on 30/04/15.
 */
object RuleOfThumb {

  def apply(num: Long): Double={
    Math.sqrt(num/2)
  }

}
