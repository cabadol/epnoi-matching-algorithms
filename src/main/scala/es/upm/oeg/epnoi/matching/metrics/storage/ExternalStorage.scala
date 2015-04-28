package es.upm.oeg.epnoi.matching.metrics.storage

import es.upm.oeg.epnoi.matching.metrics.model.item._

import scala.collection.mutable

//TODO Connect to Elasticsearch
object ExternalStorage {

  var repository: mutable.Map[String,SemanticResource] = mutable.Map.empty[String,SemanticResource]

  var uris: mutable.Map[Long,String] = mutable.Map.empty[Long,String]

  var keys: mutable.Map[String,Long] = mutable.Map.empty[String,Long]

  var index: Long = 0L

  def nextKey(uri:String): Long= {
    if (keys.contains(uri)) keys(uri)
    else {
      index = index.+(1)
      index - 1
    }
  }

  def save(resource: Resource): Unit ={
    val key   = nextKey(resource.uri)
    uris(key) = resource.uri
    keys(resource.uri) = key
    repository(resource.uri)=SemanticResource(resource,None,TopicDistribution(Array.empty))
  }

  def save(semanticResource: SemanticResource): Unit ={
    val key = nextKey(semanticResource.resource.uri)
    uris(key) = semanticResource.resource.uri
    keys(semanticResource.resource.uri) = key
    repository(semanticResource.resource.uri)=semanticResource
  }


  def read(uri: String): SemanticResource={
    repository(uri)
  }

  def keyFromUri(uri:String): Long={
    if (!keys.contains(uri)){
      val key = nextKey(uri)
      uris(key) = uri
      keys(uri) = key
      return key
    } else keys(uri)
  }

  def uriFromKey(key:Long): String={
    uris(key)
  }

  def save (key: Long, topics: Array[Double]): Unit ={
    repository(uris(key)) = repository(uris(key)).copy(topics = TopicDistribution(topics))
  }

}
