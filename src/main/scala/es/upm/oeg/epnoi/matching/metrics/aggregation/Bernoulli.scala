package es.upm.oeg.epnoi.matching.metrics.aggregation

/**
 * Created by cbadenes on 23/04/15.
 */
object Bernoulli {

  def apply (p: Array[Double], q: Array[Double]): Array[Double] ={
    p.zip(q).map(x=>(x._1+x._2)/2)
  }

}
