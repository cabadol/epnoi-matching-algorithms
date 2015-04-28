package es.upm.oeg.epnoi.matching.metrics.model.space

import es.upm.oeg.epnoi.matching.metrics.model.item._
import org.apache.spark.rdd.RDD


case class RepresentationalSpace (resources: RDD[Resource]) extends Serializable {

  private def words(resources: RDD[Resource]): RDD[String]={
    resources.flatMap{ resource =>
      resource.words match{
        case None => List.empty[String]
        case Some(words) => words
      }
    }
  }


  val vocabulary: Vocabulary = new Vocabulary(words(resources))

}
