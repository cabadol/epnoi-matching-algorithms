package es.upm.oeg.epnoi.matching.metrics.space

import es.upm.oeg.epnoi.matching.metrics.model.TopicModel
import org.apache.spark.rdd.RDD

/**
 * Collection of semantic resources described by topics
 * @param conceptSpace
 */
class TopicSpace (conceptSpace: ConceptSpace) extends Serializable {

  // Topic Model
  val model = new TopicModel(conceptSpace.featureVectors)

  // Conceptual resources with topic distributions
  val semanticResources: RDD[SemanticResource] = model.ldaModel.topicDistributions.join(conceptSpace.indexes).map{ case (key,(topics,conceptualResource)) =>
    SemanticResource(conceptualResource, TopicDistribution(topics.toArray))
  }


}
