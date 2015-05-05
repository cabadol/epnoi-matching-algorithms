package es.upm.oeg.epnoi.matching.metrics.domain

import es.upm.oeg.epnoi.matching.metrics.topics.TopicModel
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

  // Author Profiles with topic distributions
  val profiles : RDD[Profile] = semanticResources.flatMap{case semanticResource =>
    semanticResource.conceptualResource.resource.metadata.authors match{
      case None => Seq.empty[Profile]
      case Some(a) => a.map(Profile(_,Map((semanticResource.conceptualResource.resource.metadata, semanticResource.topics))))
    }
  }.groupBy(_.author).map{case (author,listProfile) => listProfile.reduce(_.+(_))}



}
