package es.upm.oeg.epnoi.matching.metrics.feature

import es.upm.oeg.epnoi.matching.metrics.domain.Vocabulary
import org.apache.spark.mllib.linalg.{Vector, Vectors}

import scala.collection.mutable


object WordCounter {

  // words is a Seq instead of RDD because this method is called inside a RDD
  def count(words: Seq[String], vocabulary: Vocabulary): Vector = {
    val counts = new mutable.HashMap[Int, Double]()
    words.foreach { word =>
      if (vocabulary.contains(word)) {
        val idx = vocabulary.key(word).toInt
        counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
      }
    }
    Vectors.sparse(vocabulary.size, counts.toSeq)
  }


}
