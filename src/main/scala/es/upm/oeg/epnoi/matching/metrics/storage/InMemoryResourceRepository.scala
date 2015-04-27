package es.upm.oeg.epnoi.matching.metrics.storage

import es.upm.oeg.epnoi.matching.metrics.model.Resource
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import scala.collection.mutable
import scala.collection.mutable.{Map,SynchronizedMap, HashMap}


/**
 * In Memory implementation of Resource Repository
 * @param input
 */
class InMemoryResourceRepository (input: RDD[Resource]) extends ResourceRepository with Serializable {


  private val uriToKey: collection.Map[String,Long] = input.map(_.uri).zipWithIndex.collectAsMap()

  private val keyToUri: collection.Map[Long,String] = uriToKey.map{case (doc,key) => (key,doc)}

  var resourceMap: scala.collection.mutable.Map[String,Resource] = new mutable.HashMap[String,Resource]()

  // Initialize map
  input.collect.foreach(x=>resourceMap += (x.uri -> x))

  // Handle Option[Resource] from map
  private def show(x: Option[Resource]) = x match {
    case Some(resource) => resource
    case None => throw new IllegalArgumentException
  }

  // Get key (Long) from resource's uri
  override def getKey(uri: String): Long={
    uriToKey(uri)
  }

  // Save resource's topic distribution
  override def save(topicDistribution: (Long, Vector)): Unit={
    val uri = keyToUri(topicDistribution._1)
    val resource =show(resourceMap.get(uri))
    resourceMap += (uri -> resource.copy(topicModel =  Some(topicDistribution._2.toArray)))
  }

  // Return list of resources
  override def resources(): Option[RDD[Resource]]={
    Some(input.map(x=>resourceMap(x.uri)))
  }

}
