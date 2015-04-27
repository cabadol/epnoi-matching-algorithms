package es.upm.oeg.epnoi.matching.metrics.algorithm

import es.upm.oeg.epnoi.matching.metrics.similarity.JensenShannonSimilarity
import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
 * Latent Dirichlet Allocation (LDA) algorithm
 * @param featureVectors
 */
class LDAAlgorithm (featureVectors: RDD[(Long, Vector)]) extends Serializable {

  // Number of topics (i.e., cluster centers)
  val numTopics           = 4         //TODO should be dynamically calculated

  // Limit on the number of iterations of EM used for learning
  val maxIterations       = 60        //TODO should be dynamically calculated

  // (alpha) Hyperparameter for prior over documents’ distributions over topics. Currently must be > 1,
  // where larger values encourage smoother inferred distributions.
  // (symmetric distribution) A high value means that each document is likely to contain a mixture of most
  // of the topics, and not any single topic specifically
  // default: 13.5
  val docConcentration    = 13.5      //TODO should be dynamically calculated

  // (beta) Hyperparameter for prior over topics’ distributions over terms (words). Currently must be > 1,
  // where larger values encourage smoother inferred distributions.
  // (symmetric distribution) A low value means that a topic may contain a mixture of just a few of the words.
  // default: 1.1
  val topicConcentration  = 1.1       //TODO should be dynamically calculated

  // If using checkpointing (set in the Spark configuration), this parameter specifies the frequency
  // with which checkpoints will be created. If maxIterations is large, using checkpointing can help
  // reduce shuffle file sizes on disk and help with failure recovery.
  // default: 10
  val checkpointInterval  = 10        //TODO should be dynamically calculated


  // Initialize the algorithm (Maximization-Expectation)
  // Create the topic model
  val start = System.currentTimeMillis
  val model: DistributedLDAModel = new LDA().
    setK(numTopics).
    setMaxIterations(maxIterations).
    setDocConcentration(docConcentration).
    setTopicConcentration(topicConcentration).run(featureVectors)
  val totalTime = System.currentTimeMillis - start
  println("LDA Execution elapsed time: %1d ms".format(totalTime))
  // Calculate the Jensen-Shannon-based Divergence similarity measure for each pair of documents
//  val matrix: RDD[(Long, Iterable[(Long, Long, Double)])] = model.topicDistributions.cartesian(model.topicDistributions).map { case (p,q) =>
//    (p._1,q._1,JensenShannonSimilarity.between(p._2.toArray, q._2.toArray))
//  }.groupBy(_._1).map(x=> (x._1,x._2.toSeq.sortBy(_._3)))


}
