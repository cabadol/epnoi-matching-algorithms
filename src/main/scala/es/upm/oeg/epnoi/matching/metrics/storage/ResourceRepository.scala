package es.upm.oeg.epnoi.matching.metrics.storage

import es.upm.oeg.epnoi.matching.metrics.model.item.Resource
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
 * Resource Storage API
 */
trait ResourceRepository {

  def save(tuple: (Long, Vector)): Unit={

  }


  def getKey(uri: String): Long={
    uri.hashCode.toLong
  }



  def resources(): Option[RDD[Resource]]={
    None
  }

}
