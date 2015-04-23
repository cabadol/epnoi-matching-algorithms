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

  // Number of topics (i.e., cluster centers)
  val numTopics           = 4         //TODO dynamically calculated

  // Limit on the number of iterations of EM used for learning
  val maxIterations       = 60        //TODO dynamically calculated

  // (alpha) Hyperparameter for prior over documents’ distributions over topics. Currently must be > 1,
  // where larger values encourage smoother inferred distributions.
  // (symmetric distribution) A high value means that each document is likely to contain a mixture of most
  // of the topics, and not any single topic specifically
  // default: 13.5
  val docConcentration    = 13.5      //TODO dynamically calculated

  // (beta) Hyperparameter for prior over topics’ distributions over terms (words). Currently must be > 1,
  // where larger values encourage smoother inferred distributions.
  // (symmetric distribution) A low value means that a topic may contain a mixture of just a few of the words.
  // default: 1.1
  val topicConcentration  = 1.1       //TODO dynamically calculated

  // If using checkpointing (set in the Spark configuration), this parameter specifies the frequency
  // with which checkpoints will be created. If maxIterations is large, using checkpointing can help
  // reduce shuffle file sizes on disk and help with failure recovery.
  // default: 10
  val checkpointInterval  = 10        //TODO dynamically calculated



  // Initialize the algorithm (Maximization-Expectation)
  val lda: LDA = new LDA().
    setK(numTopics).
    setMaxIterations(maxIterations).
    setDocConcentration(docConcentration).
    setTopicConcentration(topicConcentration)

  // Create the topic model
  val model: DistributedLDAModel = lda.run(featureVectors)

  // Calculate the Jensen-Shannon-based Divergence similarity measure for each pair of documents
  val matrix: RDD[(Long, Iterable[(Long, Long, Double)])] = model.topicDistributions.cartesian(model.topicDistributions).map { case (p,q) =>
    (p._1,q._1,JensenShannonSimilarity.between(p._2.toArray, q._2.toArray))
  }.groupBy(_._1).map(x=> (x._1,x._2.toSeq.sortBy(_._3)))

}
