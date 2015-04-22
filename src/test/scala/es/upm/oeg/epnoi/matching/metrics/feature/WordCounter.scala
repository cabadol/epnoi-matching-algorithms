package es.upm.oeg.epnoi.matching.metrics.feature

import org.apache.spark.mllib.linalg.{Vectors, Vector}
import org.apache.spark.rdd.RDD

import scala.collection.mutable


object WordCounter {


//  def count(tokens: RDD[Seq[String]]): RDD[(Long, Vector)] = {
//    tokens.zipWithIndex.map { case (tokens, id) =>
//      val counts = new mutable.HashMap[String, Double]()
//      tokens.foreach { term =>
//        counts(term) = counts.getOrElse(term, 0.0) + 1.0
//      }
//      (id, Vectors.sparse(counts.size, counts.toSeq))
//    }
//  }

    def flatCount( tokens: RDD[Seq[String]]): Array[(String, Long)] ={
      val wordCounts: Array[(String, Long)] = tokens.flatMap(_.map(_ -> 1L)).reduceByKey(_ + _).collect().sortBy(-_._2)

      println("*"*20+" WordCounter.flatCount:")
      wordCounts.foreach(x=>print(s"$x,"))
      println()

      return wordCounts
    }

    def count( tokens: RDD[String]): Array[(String, Long)] ={
      val wordCounts: Array[(String, Long)] = tokens.map(_ -> 1L).reduceByKey(_ + _).collect().sortBy(-_._2)

      println("*"*20+" WordCounter.count:")
      wordCounts.foreach(x=>print(s"$x,"))
      println()

      return wordCounts
    }

}
