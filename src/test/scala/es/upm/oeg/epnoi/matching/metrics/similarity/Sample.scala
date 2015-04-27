package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.model.{Metadata, Resource}

/**
 * Created by cbadenes on 27/04/15.
 */
object Sample {


  def main(args: Array[String]): Unit = {
    val metadata = Metadata("","","","",List.empty)
    val resource = Resource("uri",metadata,List("one","two"),None,None)
    println("Resource (before): " + resource)
    println("Resource (after): " + resource.copy(topicModel = Some(Array(2.0,3.0))))


    val sampleMap = new SampleMap()

    println("Map: "+ sampleMap.resourceMap)

  }

}
