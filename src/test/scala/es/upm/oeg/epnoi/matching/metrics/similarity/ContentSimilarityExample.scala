package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.model.Corpus
import es.upm.oeg.epnoi.matching.metrics.storage.InMemoryResourceRepository

/**
 * Created by cbadenes on 20/04/15.
 */
object ContentSimilarityExample {

  def main(args: Array[String]): Unit = {

    val start = System.currentTimeMillis

    // Process corpus
    val corpus = new Corpus(Articles.corpus,new InMemoryResourceRepository(Articles.corpus))

    // Combine all resources
    println("Getting cartesian resources...")
    val resources = corpus.resources match{
      case None => throw new IllegalArgumentException("No resources")
      case Some(resourceList) => resourceList.cartesian(resourceList)
    }

    // Get Research Object Similarity
    println("Getting similarities and sort...")
    val distances = resources.map{case (r1,r2) => (r1,r2,ROSimilarity.between(r1,r2))}
      .groupBy(_._1) //group by resource
      .map(x=> (x._1,x._2.toSeq.sortBy(_._3)))  //sort by distance
      //.collect

    // Print results
    println("Print results...")
    distances.foreach{x=>
      println("- " + x._1.uri)
      x._2.foreach{y=>
        println("\t"+y._3+"\t"+y._2.uri)
      }

    }


    val totalTime = System.currentTimeMillis - start
    println("Elapsed time: %1d ms".format(totalTime))


  }


}
