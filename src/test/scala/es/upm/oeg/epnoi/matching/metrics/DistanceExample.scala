package es.upm.oeg.epnoi.matching.metrics

/**
 * Created by cbadenes on 20/04/15.
 */
object DistanceExample {

  def main(args: Array[String]): Unit = {

    val dist1 : Array[Double] = Array(0.11359169047386547,0.40710705057193086,0.47930125895420367)
    val dist2 : Array[Double] = Array(0.8068292471633128,0.09936967923836512,0.09380107359832203)

    println("Distance: " + Distance.jensenShannon(dist1,dist2))
    println("Similarity: " + Similarity.jensenShannon(dist1,dist2))

  }

}
