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
      println(s"ro_sim[${semanticResource.resource.uri}]:")
      semanticResourceTuples.toSeq.sortBy(x=>1-x._3).foreach{ case(sr1,sr2,sim) =>
        println(s"\t${sr2.resource.uri.concat(" "*(45-sr2.resource.uri.length))}\t${sim}")
      }
    }

    // Print total time
    val totalTime = System.currentTimeMillis - start
    println("Elapsed time: %1d ms".format(totalTime))


  }


}
