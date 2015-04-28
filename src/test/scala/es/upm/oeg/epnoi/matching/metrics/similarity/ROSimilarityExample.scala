package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.corpus._
import es.upm.oeg.epnoi.matching.metrics.model.space.{RepresentationalSpace, SemanticSpace}

/**
 * Created by cbadenes on 20/04/15.
 */
object ROSimilarityExample {

  def main(args: Array[String]): Unit = {

    val start = System.currentTimeMillis

    // Representational Space
    val representationalSpace = new RepresentationalSpace(Articles.corpus)

    // Semantic Space
    val semanticSpace: SemanticSpace = new SemanticSpace(representationalSpace)

    // Research Object Similarity
    ROSimilarity.cross(semanticSpace.semanticResources).foreach { case (semanticResource, semanticResourceTuples) =>
      val builder = new StringBuilder()
      builder.append(s"ro_sim[${semanticResource.resource.uri}]: \n")
      val it = semanticResourceTuples.iterator
      while (it.hasNext) {
        val tuple = it.next()
        builder.append(s"\t·${tuple._2.resource.uri}\t${tuple._3}\n")
      }
      println(builder.toString())
    }

    // Print total time
    val totalTime = System.currentTimeMillis - start
    println("Elapsed time: %1d ms".format(totalTime))


  }


}
