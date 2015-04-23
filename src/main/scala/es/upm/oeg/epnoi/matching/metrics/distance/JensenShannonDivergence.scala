package es.upm.oeg.epnoi.matching.metrics.distance

/**
 * Created by cbadenes on 22/04/15.
 */
object JensenShannonDivergence {

  def between (p: Array[Double], q: Array[Double]): Double ={
    var sumP : Double = 0.0
    var sumQ : Double = 0.0

    for (i <- Range(0, p.length)) {
      sumP += p(i) * Math.log( (2*p(i))/(p(i)+q(i)))
      sumQ += q(i) * Math.log( (2*q(i)/(p(i)+q(i))))
    }
    sumP + sumQ
  }

}
