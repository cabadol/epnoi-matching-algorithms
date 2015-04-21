package es.upm.oeg.epnoi.matching.metrics.feature

import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.linalg.Vector

/**
 * Created by cbadenes on 20/04/15.
 */
object TFExtractor {

  def feature( numFeatures: Integer, corpus: Iterable[_]) : Vector = {
    return new HashingTF(numFeatures).transform(corpus)

  }

}
