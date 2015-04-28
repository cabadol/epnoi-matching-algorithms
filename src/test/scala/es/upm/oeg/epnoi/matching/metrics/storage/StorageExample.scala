package es.upm.oeg.epnoi.matching.metrics.storage

import es.upm.oeg.epnoi.matching.metrics.corpus.Virtual
import es.upm.oeg.epnoi.matching.metrics.model.item.Resource
import org.apache.spark.rdd.RDD

/**
  * Created by cbadenes on 28/04/15.
  */
object StorageExample {

   def main(args: Array[String]): Unit = {


     val input: RDD[Resource] = Virtual.corpus


     input.foreach(resource=>ExternalStorage.save(resource))

     println("Resource: " + ExternalStorage.read(input.first.uri))



   }

 }
