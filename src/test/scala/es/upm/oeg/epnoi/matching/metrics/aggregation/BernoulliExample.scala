package es.upm.oeg.epnoi.matching.metrics.aggregation

/**
 * Created by cbadenes on 23/04/15.
 */
object BernoulliExample {


  /**
   * baseball02.txt:	[0.04511153028533007,0.027282988502469782,0.907615763104291,0.019989718107909022]
baseball05.txt:	[0.09143620925378076,0.03708872815230271,0.8384562457129766,0.033018816880939904]
   */

  def main(args: Array[String]): Unit = {

    val p = Array(0.04511153028533007,0.027282988502469782,0.907615763104291,0.019989718107909022)
    val q = Array(0.09143620925378076,0.03708872815230271,0.8384562457129766,0.033018816880939904)

    println(s"p:${p.toList}")
    println(s"q:${q.toList}")
    println(s"Bernoulli: ${Bernoulli(p,q).toList}")

  }
}
