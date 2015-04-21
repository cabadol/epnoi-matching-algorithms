package es.upm.oeg.epnoi.matching.metrics.feature

import org.apache.spark.mllib.linalg.{Vectors, Vector}
import org.apache.spark.rdd.RDD

import scala.collection.mutable


object TermCounter {


  def count(tokens: RDD[Seq[String]]): RDD[(Long, Vector)] = {
//    tokens.zipWithIndex.map { case (tokens, id) =>
//      val counts = new mutable.HashMap[Int, Double]()
//      tokens.foreach { term =>
//        if (vocab.contains(term)) {
//          val idx = vocab(term)
//          counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
//        }
//      }
//      (id, Vectors.sparse(vocab.size, counts.toSeq))
    }
  }

}
