package es.upm.oeg.epnoi.matching.metrics.topics

import es.upm.oeg.epnoi.matching.metrics.topics.search.{LDAProblem, NSGAExecutor}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD


object LDASettings {

  var topics: Integer = 4

  // Hyper-parameter for prior over documents’ distributions over topics.
  // Currently must be > 1, where larger values encourage smoother inferred distributions.
  var alpha: Double = 13.5

  // Hyper-parameter for prior over topics’ distributions over terms (words).
  // Currently must be > 1, where larger values encourage smoother inferred distributions.
  var beta: Double = 1.1

  var maxIterations = 200

  override def toString : String = {
    return s"Topics: $topics, Alpha: $alpha, Beta: $beta, MaxIterations: $maxIterations"
  }

  /**
   * Learn best configuration using a Genetic Algorithm
   * @param featureVectors
   * @param maxEvaluations
   */
  def learn(featureVectors: RDD[(Long, Vector)], maxEvaluations: Integer, ldaIterations: Integer): Unit ={
    val solution = NSGAExecutor.search(new LDAProblem(featureVectors, ldaIterations),maxEvaluations)
    topics = solution.getTopics
    alpha = solution.getAlpha
    beta = solution.getBeta
    println(s"LDA parameters adjusted to solution obtained by NSGA algorithm: $toString")
  }

}
