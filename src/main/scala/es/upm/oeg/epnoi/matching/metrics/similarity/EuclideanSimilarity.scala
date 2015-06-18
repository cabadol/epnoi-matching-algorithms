package es.upm.oeg.epnoi.matching.metrics.similarity

import org.apache.spark.mllib.linalg.{Vector, Vectors}

/**
 * Created by cbadenes on 05/05/15.
 */
object EuclideanSimilarity {

  def apply( v1: Vector, v2: Vector): Double={
    1 / (1 - Vectors.sqdist(v1,v2))
  }
}
