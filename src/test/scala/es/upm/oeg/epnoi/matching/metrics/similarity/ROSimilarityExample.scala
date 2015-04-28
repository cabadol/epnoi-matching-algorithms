package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.corpus.Articles
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
    semanticSpace.semanticResources.cartesian(semanticSpace.semanticResources).map{ case(sr1,sr2)=>
      val sim = ROSimilarity(sr1,sr2)
      (sr1,sr2,sim)
    }.groupBy(_._1).map(x=>(x._1,x._2.toSeq.sortBy(_._3))).foreach{ case (sr,list) =>
      println(s"sim[${sr.resource.uri}]")
      list.foreach{ case (sr1,sr2,sim) =>
        println(s"\t·${sr2.resource.uri}\t$sim")
      }
    }



    val totalTime = System.currentTimeMillis - start
    println("Elapsed time: %1d ms".format(totalTime))


  }


}
