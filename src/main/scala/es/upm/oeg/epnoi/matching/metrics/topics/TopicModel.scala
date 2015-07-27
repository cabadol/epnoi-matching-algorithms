package es.upm.oeg.epnoi.matching.metrics.topics

import org.apache.spark.mllib.clustering.{LDAModel, DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory

/**
 * Latent Dirichlet Allocation (LDA) algorithm
 * @param featureVectors
 */
class TopicModel (featureVectors: RDD[(Long, Vector)]) extends Serializable {

  val log = LoggerFactory.getLogger(classOf[TopicModel]);

  // Create the topic model. (Maximization-Expectation Algorithm)
  val start = System.currentTimeMillis

  //TODO Spark 1.4.1 return LDAModel instead of DistributedLDAModel
  log.info(s"Building a Latent Dirichlet Allocation (LDA) model with parameters: $LDASettings")
  val ldaModel: DistributedLDAModel = new LDA().
    setK(LDASettings.topics).
    setMaxIterations(LDASettings.maxEvals).
    setDocConcentration(LDASettings.alpha).
    setTopicConcentration(LDASettings.beta).
    run(featureVectors).asInstanceOf[DistributedLDAModel]


  val totalTime = System.currentTimeMillis - start
  log.info("LDA Execution elapsed time: %1d ms".format(totalTime))
  log.info("Log-Likelihood: \t" + ldaModel.logLikelihood)
  log.info("Log-Prior: \t" + ldaModel.logPrior)

}
