package es.upm.oeg.epnoi.matching.metrics.distance

import es.upm.oeg.epnoi.matching.metrics.similarity.JensenShannonSimilarity

/**
 * Created by cbadenes on 20/04/15.
 */
object JensenShannonExample {

  def main(args: Array[String]): Unit = {

    val dist1 : Array[Double] = Array(0.11359169047386547,0.40710705057193086,0.47930125895420367)
    val dist2 : Array[Double] = Array(0.8068292471633128,0.09936967923836512,0.09380107359832203)

    println("Distance: " + JensenShannonDivergence(dist1,dist2))
    println("Similarity: " + JensenShannonSimilarity(dist1,dist2))

  }

}
