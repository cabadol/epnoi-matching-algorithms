package es.upm.oeg.epnoi.matching.metrics.distance

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * Created by cbadenes on 20/04/15.
 */
@RunWith(classOf[JUnitRunner])
class JensenShannonDivergenceTest extends FunSuite {

  test("Jensen-Shannon Similarity of two vectors") {
    val dist1 : Array[Double] = Array(0.11359169047386547,0.40710705057193086,0.47930125895420367)
    val dist2 : Array[Double] = Array(0.8068292471633128,0.09936967923836512,0.09380107359832203)

    assert(JensenShannonDivergence(dist1, dist2) == 0.5361737075139519)
  }


}