package es.upm.oeg.epnoi.matching.metrics.feature

import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 20/04/15.
 */
object TFCounter {

  def count(tokens: RDD[Seq[String]]): RDD[Vector] = {
    new HashingTF(10000).transform(tokens)
  }

}
