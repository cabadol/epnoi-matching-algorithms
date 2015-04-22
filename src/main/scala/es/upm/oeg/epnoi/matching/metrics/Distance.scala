package es.upm.oeg.epnoi.matching.metrics

/**
 * Created by cbadenes on 20/04/15.
 */
object Distance {


  def jensenShannon (p: Array[Double], q: Array[Double]): Double ={
    var sumP : Double = 0.0
    var sumQ : Double = 0.0

    for (i <- Range(0, p.length)) {
      sumP += p(i) * Math.log( (2*p(i))/(p(i)+q(i)))
      sumQ += q(i) * Math.log( (2*q(i)/(p(i)+q(i))))
    }
    sumP + sumQ
  }


  def hellinger(p: Array[Double], q: Array[Double]): Double ={
    0.0
  }

  def manhattan(p: Array[Double], q: Array[Double]): Double ={
    0.0
  }

}
