package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.corpus._
import es.upm.oeg.epnoi.matching.metrics.domain.{ConceptSpace, ConceptualResource, TopicSpace}

/**
 * Created by cbadenes on 20/04/15.
 */
object AuthorsProfileExample {

  def main(args: Array[String]): Unit = {

    // Conceptual resources
    val conceptualResources = Code.corpus.map(ConceptualResource(_))

    // Concept Space
    val conceptSpace = new ConceptSpace(conceptualResources)

    // Topic Space
    val topicSpace: TopicSpace = new TopicSpace(conceptSpace)

    // Print profiles
    topicSpace.profiles.foreach{ profile =>
      println(s"Profile: $profile")
    }

    // Similarity between authors profile

    topicSpace.profiles.cartesian(topicSpace.profiles).foreach{ case (p1,p2) =>
      println(s"${p1.author.uri} <=> ${p2.author.uri}:\t${AuthorsSimilarity(p1,p2)}")
    }


    println("-"*20+" Global Author Similarity:")
    println(AuthorsSimilarity(topicSpace.profiles,topicSpace.profiles))

  }


}
