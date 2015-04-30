package es.upm.oeg.epnoi.matching.metrics.distribution

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.apache.commons.math3.distribution.NormalDistribution

/**
 * Created by cbadenes on 20/04/15.
 */
@RunWith(classOf[JUnitRunner])
class NormalDistributionTest extends FunSuite {

  test("Jensen-Shannon Similarity of two vectors") {

    assert(new NormalDistribution(0,1).density(0) == 0.3989422804014327)

    assert(new NormalDistribution(1,1).density(1) == 0.3989422804014327)

    assert(new NormalDistribution(2,1).density(2) == 0.3989422804014327)

  }


}