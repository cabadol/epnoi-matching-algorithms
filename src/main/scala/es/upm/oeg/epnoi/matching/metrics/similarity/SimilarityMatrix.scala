package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.domain.entity.{TopicalResource, AuthorProfile}
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 23/04/15.
 *
 * Measure of similarity between two authors profile or group of authors that measures the Jensen-Shannon Divergence
 * between their topics distribution
 */
object SimilarityMatrix {


  def apply(topicalResources: RDD[TopicalResource]): RDD[(TopicalResource, Iterable[(TopicalResource, TopicalResource, Double)])] = {
    topicalResources.cartesian(topicalResources).map{case(sr1,sr2)=>(sr1,sr2,TopicsSimilarity(sr1.topics,sr2.topics))}.groupBy(_._1)
  }

}
