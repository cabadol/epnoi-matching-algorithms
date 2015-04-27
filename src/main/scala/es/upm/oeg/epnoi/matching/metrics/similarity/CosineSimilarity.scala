package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.distance.JensenShannonDivergence
import org.apache.spark.mllib.feature.Normalizer

/**
 * Created by cbadenes on 23/04/15.
 */
object CosineSimilarity {

  def between(p: Array[Double], q: Array[Double]): Double = {

    var sum: Double   = 0
    var sump: Double  = 0
    var sumq: Double  = 0

    for (i <- Range(0,p.length)){

      sum += p(i)*q(i)
      sump += Math.pow(p(i),2)
      sumq += Math.pow(q(i),2)
    }

    sum/(Math.sqrt(sump)*Math.sqrt(sumq))

  }

}
