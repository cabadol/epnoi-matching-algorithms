package es.upm.oeg.epnoi.matching.metrics.statistic

import es.upm.oeg.epnoi.matching.metrics.heuristic.{Evaluation, LDAOptimizer, LDAParameters}
import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
 * Latent Dirichlet Allocation (LDA) algorithm
 * @param featureVectors
 */
class GenerativeModel (featureVectors: RDD[(Long, Vector)], optimized: Boolean) extends Serializable with Evaluation {


  def lda (param: LDAParameters) = new LDA().
    setK(param.getTopics).
    setMaxIterations(param.getMaxIterations).
    setDocConcentration(param.getAlpha).
    setTopicConcentration(param.getBeta).
    run(featureVectors)

  def evaluate(parameters: LDAParameters) = {
    val model = lda(parameters)
    println(s"loglikelihood: " + model.logLikelihood)
    println(s"logprior: " + model.logPrior)
    Math.abs(model.logLikelihood)
  }

  val size = featureVectors.count()

  val parameters: LDAParameters = if (optimized){
    // Searchbest parameter configuration by Genetic Algorithm
    // Non-dominated Sorting Genetic Algorithm (NSGAII)
    LDAOptimizer.search(size,this).setMaxIterations(200)
  }else{
    // topics:  Number of cluster centers
    //   default: 4
    // alpha:   Hyperparameter for prior over documents’ distributions over topics. Currently must be > 1,
    //   where larger values encourage smoother inferred distributions.
    //   (symmetric distribution) A high value means that each document is likely to contain a mixture of most
    //   of the topics, and not any single topic specifically
    //   default: 13.5
    // beta:    Hyperparameter for prior over topics’ distributions over terms (words). Currently must be > 1,
    //   where larger values encourage smoother inferred distributions.
    //   (symmetric distribution) A low value means that a topic may contain a mixture of just a few of the words.
    //   default: 1.1
    new LDAParameters(4,13.5,1.1,200,size.toInt )
  }


  // Initialize the algorithm (Maximization-Expectation)
  // Create the topic model
  val start = System.currentTimeMillis
  println(s"Considered the following Latent Dirichlet Allocation (LDA) parameters: ")
  println(s"\t·Topics: ${parameters.getTopics}")
  println(s"\t·Alpha: ${parameters.getAlpha}")
  println(s"\t·Beta: ${parameters.getBeta}")
  println(s"Training the model...")
  private val distributedLDAModel: DistributedLDAModel = new LDA().
    setK(parameters.getTopics).
    setMaxIterations(parameters.getMaxIterations).
    setDocConcentration(parameters.getAlpha).
    setTopicConcentration(parameters.getBeta).
    run(featureVectors)
  val totalTime = System.currentTimeMillis - start
  println("LDA Execution elapsed time: %1d ms".format(totalTime))
  println("Log-Likelihood: " + distributedLDAModel.logLikelihood)

  def topicDistributions = distributedLDAModel.topicDistributions

  def topicsMatrix = distributedLDAModel.topicsMatrix


}
