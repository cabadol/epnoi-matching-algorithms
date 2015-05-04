package es.upm.oeg.epnoi.matching.metrics.model

import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
 * Latent Dirichlet Allocation (LDA) algorithm
 * @param featureVectors
 */
class TopicModel (featureVectors: RDD[(Long, Vector)]) extends Serializable {

  // Create the topic model. (Maximization-Expectation Algorithm)
  val start = System.currentTimeMillis

  println(s"Building a Latent Dirichlet Allocation (LDA) model with parameters: $LDASettings")
  val ldaModel: DistributedLDAModel = new LDA().
    setK(LDASettings.topics).
    setMaxIterations(LDASettings.maxIterations).
    setDocConcentration(LDASettings.alpha).
    setTopicConcentration(LDASettings.beta).
    run(featureVectors)

  val totalTime = System.currentTimeMillis - start
  println("LDA Execution elapsed time: %1d ms".format(totalTime))
  println("Log-Likelihood: \t" + ldaModel.logLikelihood)
  println("Log-Prior: \t" + ldaModel.logPrior)
}
