package es.upm.oeg.epnoi.matching.metrics.feature

import org.apache.spark.rdd.RDD


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



}
