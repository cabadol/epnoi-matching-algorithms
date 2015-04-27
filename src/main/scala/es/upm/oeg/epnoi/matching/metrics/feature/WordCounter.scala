package es.upm.oeg.epnoi.matching.metrics.feature

import es.upm.oeg.epnoi.matching.metrics.model._
import org.apache.spark.mllib.linalg.{Vectors, Vector}
import org.apache.spark.rdd.RDD

import scala.collection.mutable


object WordCounter {


    def flatCount( tokens: RDD[Seq[String]]): Array[(String, Long)] ={
      tokens.flatMap(_.map(_ -> 1L)).reduceByKey(_ + _).collect().sortBy(-_._2)
    }

    def count( tokens: RDD[String]): Array[(String, Long)] ={
      tokens.map(_ -> 1L).reduceByKey(_ + _).collect().sortBy(-_._2)
    }

    def count( tokens: Seq[String]): Seq[(String, Long)] ={
      tokens.map((_,1L))
    }


    def count(terms: Seq[String], vocabulary: Vocabulary): Vector = {
      val counts = new mutable.HashMap[Int, Double]()
      terms.foreach { term =>
        if (vocabulary.contains(term)) {
          val idx = vocabulary.key(term).toInt
          counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
        }
      }
      Vectors.sparse(vocabulary.size, counts.toSeq)
    }


}
