package es.upm.oeg.epnoi.matching.metrics.topics

import es.upm.oeg.epnoi.matching.metrics.topics.search.{LDAProblem, NSGAExecutor}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory


object LDASettings {

  val log = LoggerFactory.getLogger(LDASettings.getClass);

  var topics: Integer = 4

  // Hyper-parameter for prior over documents’ distributions over topics.
  // Currently must be > 1, where larger values encourage smoother inferred distributions.
  var alpha: Double = 13.5

  // Hyper-parameter for prior over topics’ distributions over terms (words).
  // Currently must be > 1, where larger values encourage smoother inferred distributions.
  var beta: Double = 1.1

  var maxEvals = 200

  def setTopics(number: Integer): Unit ={
    topics = number
  }

  def setAlpha(number: Double): Unit ={
    alpha = number
  }

  def setBeta(number: Double): Unit={
    beta = number
  }

  def setMaxIterations(number: Integer): Unit ={
    maxEvals = number
  }

  override def toString : String = {
    return s"Topics: $topics, Alpha: $alpha, Beta: $beta, MaxIterations: $maxEvals"
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
    maxEvals = ldaIterations
    log.info(s"LDA parameters adjusted to solution obtained by NSGA algorithm: $toString");
  }

}
