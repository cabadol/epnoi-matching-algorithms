package es.upm.oeg.epnoi.matching.metrics.model

import es.upm.oeg.epnoi.matching.metrics.algorithm.LDAAlgorithm
import es.upm.oeg.epnoi.matching.metrics.feature.WordCounter
import es.upm.oeg.epnoi.matching.metrics.storage.ResourceRepository
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD


class Corpus (input: RDD[Resource], repository: ResourceRepository) extends Serializable {

  // Terms from corpus
  println("Creating vocabulary..")
  val vocabulary: Vocabulary = new Vocabulary(input)

  // Term Frequency vectors
  println("Creating Feature Vectors..")
  val featureVectors: RDD[(Long,Vector)] = input.map(x=>(repository.getKey(x.uri),WordCounter.count(x.terms, vocabulary)))

  // Run algorithm
  //val lda = new LDAAlgorithm(featureVectors)

  // Save/Update topic distribution for each resource in repository
  println("Executing LDA..")
  new LDAAlgorithm(featureVectors).model.topicDistributions.collect.foreach(repository.save(_))

  // Resources with topic models updated
  def resources = repository.resources

}
