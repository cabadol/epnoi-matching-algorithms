package es.upm.oeg.epnoi.matching.metrics.aggregation

/**
 * Created by cbadenes on 23/04/15.
 */
object Bernoulli {

  def joint (p: Array[Double], q: Array[Double]): Array[Double] ={

    var res: Array[Double] = Array.fill(p.length)(0.0)

    for (i <- Range(0,p.length)){
      res(i) = (p(i)+q(i))/2
    }
    return res
  }

}
