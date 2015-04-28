package es.upm.oeg.epnoi.matching.metrics.aggregation

import es.upm.oeg.epnoi.matching.metrics.similarity.CosineSimilarity
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * Created by cbadenes on 23/04/15.
 */
@RunWith(classOf[JUnitRunner])
class BernoulliTest extends FunSuite {


  test("Bernoulli combination of two vectors") {

    val p = Array(0.04511153028533007,0.027282988502469782,0.907615763104291,0.019989718107909022)
    val q = Array(0.09143620925378076,0.03708872815230271,0.8384562457129766,0.033018816880939904)

    assert(Bernoulli(p, q).deep == Array(0.06827386976955542, 0.03218585832738624, 0.8730360044086338, 0.026504267494424465).deep)
  }
}
