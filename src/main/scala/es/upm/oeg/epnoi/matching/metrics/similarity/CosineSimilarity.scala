package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.distance.JensenShannonDivergence
import org.apache.spark.mllib.feature.Normalizer

/**
 * Created by cbadenes on 23/04/15.
 */
object CosineSimilarity {

  def apply(p: Array[Double], q: Array[Double]): Double = {

    val sum = p.zip(q).map(x=>(x._1*x._2, Math.pow(x._1,2), Math.pow(x._2,2))).reduce((x,y)=>(x._1+y._1, x._2+y._2, x._3+y._3))
    sum._1/(Math.sqrt(sum._2)*Math.sqrt(sum._3))


  }

//  def apply(p: Array[Double], q: Array[Double]): Double = {
//
//    var sum: Double   = 0
//    var sump: Double  = 0
//    var sumq: Double  = 0
//
//    for (i <- Range(0,p.length)){
//
//      sum += p(i)*q(i)
//      sump += Math.pow(p(i),2)
//      sumq += Math.pow(q(i),2)
//    }
//
//    sum/(Math.sqrt(sump)*Math.sqrt(sumq))
//
//  }

}
