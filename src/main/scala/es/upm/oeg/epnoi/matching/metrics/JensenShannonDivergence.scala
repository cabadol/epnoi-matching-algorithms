package es.upm.oeg.epnoi.matching.metrics

/**
 * Created by cbadenes on 20/04/15.
 */
object JensenShannonDivergence {


  def distance (dist1: Array[Double], dist2: Array[Double]): Double ={
    var sum: Double = 0.0
    for (i <- Range(0, dist1.length)) {
      val oper1 = if (dist1(i) > 0 ) (dist1(i)/2.0) * Math.log( (2.0 * dist1(i)) / (dist1(i) + dist2(i))) else 0
      val oper2 = if (dist2(i) > 0 ) (dist2(i)/2.0) * Math.log( (2.0 * dist2(i)) / (dist1(i) + dist2(i))) else 0
      sum = sum.+(oper1+oper2)
    }
    return sum
  }

}
