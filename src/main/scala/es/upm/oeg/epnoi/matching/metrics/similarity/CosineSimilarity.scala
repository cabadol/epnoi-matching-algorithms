package es.upm.oeg.epnoi.matching.metrics.similarity

/**
 * Created by cbadenes on 23/04/15.
 *
 * Measure of similarity between two vectors that measures the cosine of the angle between them
 */
object CosineSimilarity {

  def apply(p: Array[Double], q: Array[Double]): Double = {

    val sum = p.zip(q).map(x=>(x._1*x._2, Math.pow(x._1,2), Math.pow(x._2,2))).reduce((x,y)=>(x._1+y._1, x._2+y._2, x._3+y._3))
    sum._1/(Math.sqrt(sum._2)*Math.sqrt(sum._3))


  }

}
