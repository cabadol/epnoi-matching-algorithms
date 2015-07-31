package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.corpus._
import es.upm.oeg.epnoi.matching.metrics.domain.entity.{ConceptualResource, TopicalResource}
import es.upm.oeg.epnoi.matching.metrics.domain.space.{ConceptsSpace, TopicsSpace}
import es.upm.oeg.epnoi.matching.metrics.topics.LDASettings
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

import scala.collection.Map
import scala.collection.parallel.mutable

/**
 * Created by cbadenes on 20/04/15.
 */
object LDAWordsExample {


  def main(args: Array[String]): Unit = {

    val start = System.currentTimeMillis

    // Conceptual resources
    val conceptualResources = Articles.corpus.map(ConceptualResource(_))

    // Concept Space
    val conceptSpace = new ConceptsSpace(conceptualResources)

    LDASettings.setTopics(4);
    LDASettings.setAlpha(13.5);
    LDASettings.setBeta(1.1);
    LDASettings.setMaxIterations(50);

    // Topic Space
    val topicSpace: TopicsSpace = new TopicsSpace(conceptSpace)


    val topicsDistribution: RDD[(Long, Vector)] = topicSpace.model.ldaModel.topicDistributions


    println ("Vocabulary Size: " +conceptSpace.vocabulary.size)

    // Semantic Resources
//    val semanticResources: RDD[TopicalResource] = topicSpace.topicalResources

    var vocabDist = scala.collection.mutable.HashMap.empty[Int,Array[Double]]

    // Distribution of words by topics
    val size = 100
    val topics: Array[(Array[Int], Array[Double])] = topicSpace.model.ldaModel.describeTopics(size)
    var index = 0
    topics.foreach{ t =>
      for (x <- 0 until size){
        val w = t._1(x)
        val d = t._2(x)

        if (!vocabDist.contains(w)){
          vocabDist(w)=Array.fill[Double](topics.size)(0.0)
        }

        vocabDist(w)(index)= f"$d%1.4f".toDouble

//        conceptSpace.vocabulary.wordsByKeyMap.get(t._1(x)) match{
//          case Some(w) => println("Word: " + w + "  [" + t._2(x) + "]")
//          case _ => println("unknown")
//        }

      }
      index +=1
    }



    vocabDist.keysIterator.foreach{case w =>
        println(vocabDist.get(w).get.mkString(","))
    }

    // Print total time
    val totalTime = System.currentTimeMillis - start
    println("Elapsed time: %1d ms".format(totalTime))



  }


}
