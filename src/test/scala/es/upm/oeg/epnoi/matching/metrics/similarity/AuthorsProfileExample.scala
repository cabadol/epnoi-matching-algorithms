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

    // Print similarities between author profiles
    topicSpace.authorProfiles.foreach{ ap1 =>
      println(s"Author Profile: $ap1")
      topicSpace.authorProfiles.foreach{ ap2 =>
        println(s"\t${ap2.author.uri}\t${AuthorsSimilarity(ap1,ap2)}")
      }
    }

    println("-"*20+" Global Author Similarity:")
    println(AuthorsSimilarity(topicSpace.authorProfiles,topicSpace.authorProfiles))

  }


}
