package es.upm.oeg.epnoi.matching.metrics.domain.space

import es.upm.oeg.epnoi.matching.metrics.domain.entity.{AuthorProfile, TopicDistribution, TopicalResource}
import es.upm.oeg.epnoi.matching.metrics.similarity.{AuthorsSimilarity, TopicsSimilarity}
import es.upm.oeg.epnoi.matching.metrics.topics.TopicModel
import org.apache.spark.rdd.RDD

/**
 * Collection of semantic resources described by topics
 * @param conceptSpace
 */
class TopicsSpace (conceptSpace: ConceptsSpace) extends Serializable {

  // Topic Model
//  val model = new TopicModel(conceptSpace.featureVectors)

  // Conceptual resources with topic distributions
  val topicalResources: RDD[TopicalResource] = new TopicModel(conceptSpace.featureVectors).ldaModel.topicDistributions.join(conceptSpace.conceptualResourcesMap).map{ case (key,(topics,conceptualResource)) =>
    TopicalResource(conceptualResource, TopicDistribution(topics.toArray))
  }

  // Authors with topic distributions
  // Because SPARK-5063 nested actions in RDD are not allowed
  val authorProfiles : Seq[AuthorProfile] = topicalResources.flatMap{ semanticResource =>
    semanticResource.conceptualResource.resource.metadata.authors match{
      case None => Seq.empty[AuthorProfile]
      case Some(a) => a.map(author=>AuthorProfile(author,Map((semanticResource.conceptualResource.resource.metadata,semanticResource.topics))))
    }
  }.groupBy(_.author.uri).map{case (uri,listAuthorProfiles) => listAuthorProfiles.reduce(_+_)}.collect


  def authorsOf(semanticResource: TopicalResource): Seq[AuthorProfile] ={
    authorProfiles.filter(_.publications.contains(semanticResource.conceptualResource.resource.metadata))
  }

  /**
   * Research Object Similarity
   * @param sr1
   * @param sr2
   * @return
   */
  def similarity(sr1: TopicalResource, sr2: TopicalResource): Double={

    val authorsSimilarity = AuthorsSimilarity(authorsOf(sr1),authorsOf(sr2))
    val contentSimilarity = TopicsSimilarity(sr1.topics,sr2.topics)
    val alpha = 0.7

    alpha*contentSimilarity + (1-alpha)*authorsSimilarity
  }

  /**
   * Similarity matrix from cartesian product of semantic resources
   * @param semanticResources
   * @return RDD[(SemanticResource,Iterable[(SemanticResource,SemanticResource,Double)])]
   */
  def cross(semanticResources: RDD[TopicalResource]): RDD[(TopicalResource,Iterable[(TopicalResource,TopicalResource,Double)])]={

    semanticResources.cartesian(semanticResources).map{case(sr1,sr2)=>(sr1,sr2,similarity(sr1,sr2))}.groupBy(_._1)
  }


}
