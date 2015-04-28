package es.upm.oeg.epnoi.matching.metrics.model

import es.upm.oeg.epnoi.matching.metrics.corpus.Virtual
import es.upm.oeg.epnoi.matching.metrics.model.item.Resource
import org.apache.spark.rdd.RDD

/**
  * Created by cbadenes on 28/04/15.
  */
object ResourcesExample {

   def main(args: Array[String]): Unit = {


     val input: RDD[Resource] = Virtual.corpus


     def documents(resource: Resource): Seq[Resource]={
       resource.resources match{
         case None => List(resource)
         case Some(resources) => resources.flatMap(documents(_))
       }
     }


     println("Resources: " + input.flatMap(documents(_)).collect().toList)

   }

 }
