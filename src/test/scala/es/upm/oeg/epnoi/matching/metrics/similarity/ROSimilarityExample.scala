package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.corpus._
import es.upm.oeg.epnoi.matching.metrics.domain.entity.{TopicalResource, ConceptualResource}
import es.upm.oeg.epnoi.matching.metrics.domain.space.{ConceptsSpace, TopicsSpace}
import es.upm.oeg.epnoi.matching.metrics.topics.LDASettings
import org.apache.spark.rdd.RDD

import scala.collection.Map

/**
 * Created by cbadenes on 20/04/15.
 */
object ROSimilarityExample {


  def main(args: Array[String]): Unit = {

    val start = System.currentTimeMillis

    // Conceptual resources
    val conceptualResources = Articles.corpus.map(ConceptualResource(_))

    // Concept Space
    val conceptSpace = new ConceptsSpace(conceptualResources)

    // OPTIMIZATION: Search best parameters (topics, alpha and beta) for LDA process
//    LDASettings.learn(conceptSpace.featureVectors, maxEvaluations = 5, ldaIterations = 50)

    LDASettings.setTopics(4);
    LDASettings.setAlpha(13.5);
    LDASettings.setBeta(1.1);
    LDASettings.setMaxIterations(50);

    // Topic Space
    val topicSpace: TopicsSpace = new TopicsSpace(conceptSpace)

    // Semantic Resources
    val semanticResources: RDD[TopicalResource] = topicSpace.topicalResources

    // Similarity matrix

//    val matrix = topicSpace.cross(semanticResources)

    val matrix: RDD[(TopicalResource, Iterable[(TopicalResource, TopicalResource, Double)])] = semanticResources.cartesian(semanticResources).map{case(sr1,sr2)=>(sr1,sr2,TopicsSimilarity(sr1.topics,sr2.topics))}.groupBy(_._1)



    val resourceSize = conceptualResources.count
    val matrixSize = matrix.count
    println(s"Resource Size: $resourceSize and Matrix Size: $matrixSize")

    assert( resourceSize == matrixSize)

    // Print Resources Similarity Matrix
    matrix.foreach { case (semanticResource, semanticResourceTuples) =>
      println(s"ro_sim[${semanticResource.conceptualResource.resource.uri}]:")
      semanticResourceTuples.toSeq.sortBy(x=>1-x._3).foreach{ case(sr1,sr2,sim) =>
        println(s"\t${sr2.conceptualResource.resource.uri.concat(" "*(45-sr2.conceptualResource.resource.uri.length))}\t${sim}")
      }
    }


    // Distribution of topics by documents
    val resourcesMap: Map[Long, ConceptualResource] = conceptSpace.conceptualResourcesMap.collectAsMap();

    topicSpace.model.ldaModel.topicDistributions.collect.foreach{ case (id,v) =>

      resourcesMap.get(id) match {
        case Some(x) => print("- " + x.resource.metadata.title)
        case _ => print("unknown")
      }
      print(" (")
      v.toArray.foreach{ p =>
        print(p+",")
      }
      println(")")
    }

    // Distribution of words by topics

    val topics: Array[(Array[Int], Array[Double])] = topicSpace.model.ldaModel.describeTopics()
    var index = 0
    topics.foreach{ t =>
      println ("Topic: " + index)
      index +=1
      for (x <- 0 until 10){
        conceptSpace.vocabulary.wordsByKeyMap.get(t._1(x)) match{
          case Some(w) => println("Word: " + w + " - " + t._2(x))
          case _ => println("unknown")
        }

      }

    }

    // Print total time
    val totalTime = System.currentTimeMillis - start
    println("Elapsed time: %1d ms".format(totalTime))



  }


}
