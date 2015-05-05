package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.corpus._
import es.upm.oeg.epnoi.matching.metrics.topics.LDASettings
import es.upm.oeg.epnoi.matching.metrics.domain.{ConceptualResource, ConceptSpace, WordSpace, TopicSpace}
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 20/04/15.
 */
object ROSimilarityExample {

  def main(args: Array[String]): Unit = {

    val start = System.currentTimeMillis

    // Conceptual resources
    val conceptualResources = Articles.corpus.map(ConceptualResource(_))

    // Concept Space
    val conceptSpace = new ConceptSpace(conceptualResources)

    // OPTIMIZATION: Search best parameters (topics, alpha and beta) for LDA process
    //LDASettings.adjust(conceptSpace.featureVectors)

    // Topic Space
    val topicSpace: TopicSpace = new TopicSpace(conceptSpace)

    // Semantic Resources
    val semanticResources = topicSpace.semanticResources

    // Similarity matrix
    val matrix = ROSimilarity.cross(semanticResources)

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

    // Print total time
    val totalTime = System.currentTimeMillis - start
    println("Elapsed time: %1d ms".format(totalTime))



  }


}
