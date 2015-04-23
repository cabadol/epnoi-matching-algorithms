package es.upm.oeg.epnoi.matching.metrics.similarity

import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
 * Text Similarity measure based on a topic model created from Term-Frequency vectors for each document.
 * Model described by a Latent Dirichlet Allocation (LDA) implemented by a Distributed Expectation-Maximization algorithm
 * @param featureVectors
 */
class TextSimilarity (featureVectors: RDD[(Long, Vector)]) extends Serializable {

  // Calculate parameters from feature vectors
  val numTopics = 4       //TODO dynamically calculated
  val maxIterations = 60  //TODO dynamically calculated

  // Initialize the algorithm (Maximization-Expectation)
  val lda: LDA = new LDA().setK(numTopics).setMaxIterations(maxIterations)

  // Create the topic model
  val model: DistributedLDAModel = lda.run(featureVectors)

  // Calculate the Jensen-Shannon-based Divergence similarity measure for each document
  val matrix: RDD[(Long,Long,Double)] = model.topicDistributions.cartesian(model.topicDistributions).map { case (p,q) =>
    (p._1,q._1,JensenShannonSimilarity.between(p._2.toArray, q._2.toArray))
  }

  def printDetails(): Unit ={

  }
}
