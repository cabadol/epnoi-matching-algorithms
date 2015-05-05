package es.upm.oeg.epnoi.matching.metrics.topics

import es.upm.oeg.epnoi.matching.metrics.topics.search.{LDAProblem, NSGAExecutor}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD


object LDASettings {

  var topics: Integer = 4

  var alpha: Double = 13.5

  var beta: Double = 1.1

  var maxIterations = 200

  override def toString : String = {
    return s"Topics: $topics, Alpha: $alpha, Beta: $beta, MaxIterations: $maxIterations"
  }

  /**
   * Learn best configuration using a Genetic Algorithm
   * @param featureVectors
   */
  def adjust(featureVectors: RDD[(Long, Vector)]): Unit ={
    val problem = new LDAProblem(featureVectors);
    val maxEvaluations = featureVectors.count()*2 // TODO set boundaries
    val solution = NSGAExecutor.search(problem,maxEvaluations.toInt)
    topics = solution.getTopics
    alpha = solution.getAlpha
    beta = solution.getBeta
    println(s"LDA parameters adjusted to solution obtained by NSGA algorithm: $toString")
  }

}
