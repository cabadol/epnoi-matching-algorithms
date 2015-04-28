package es.upm.oeg.epnoi.matching.metrics.model

import es.upm.oeg.epnoi.matching.metrics.corpus.{Virtual, Articles}
import es.upm.oeg.epnoi.matching.metrics.model.item.Resource
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 28/04/15.
 */
object VocabularyExample {

  def main(args: Array[String]): Unit = {


    val input: RDD[Resource] = Virtual.corpus

    def words(resources: RDD[Resource]): RDD[String]={
      resources.flatMap{ resource =>
        resource.words match{
          case None => List.empty[String]
          case Some(words) => words
        }
      }
    }


   println("Words: " + words(input).collect().toList)


    var index: Long = 0L

    index.+(1)

    println("Index: " + index)


  }

}
