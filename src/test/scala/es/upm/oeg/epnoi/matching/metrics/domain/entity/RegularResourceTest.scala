package es.upm.oeg.epnoi.matching.metrics.domain.entity

import es.upm.oeg.epnoi.matching.metrics.aggregation.Bernoulli
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * Created by cbadenes on 05/06/15.
 */
@RunWith(classOf[JUnitRunner])
class RegularResourceTest  extends FunSuite {


  test("Feature Vector of aggregated regular-resources") {

    val vocabulary = new Vocabulary(SparkWrapper.sc.parallelize(Seq("uno","dos")))

    val m1: Metadata = Metadata("title1","date1",Seq.empty)

    val r11 = new RegularResource("uri1","url1",m1,Seq("uno","uno"),Seq.empty)

    val r12 = new RegularResource("uri1","url1",m1,Seq("uno","dos"),Seq.empty)

    val r1 = new RegularResource("uri1","url1",m1,Seq("uno"),Seq(r11,r12))

    val result = r1.featureVector(vocabulary);

    println(s"Feature Vector: $result")





//    assert(Bernoulli(p, q).deep == Array(0.06827386976955542, 0.03218585832738624, 0.8730360044086338, 0.026504267494424465).deep)
  }
}
