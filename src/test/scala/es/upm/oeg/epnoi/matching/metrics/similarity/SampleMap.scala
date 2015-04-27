package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.model.Resource
import es.upm.oeg.epnoi.matching.metrics.storage.ResourceRepository

import scala.collection.mutable

/**
 * Created by cbadenes on 27/04/15.
 */
class SampleMap extends ResourceRepository with Serializable{

  var resourceMap: scala.collection.mutable.Map[String,String] = new mutable.HashMap[String,String]()

  List("1","2").foreach(x=>resourceMap += (x -> x))

}
