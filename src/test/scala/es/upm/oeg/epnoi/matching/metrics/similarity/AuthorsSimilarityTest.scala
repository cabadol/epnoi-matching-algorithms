package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.topics.Authors
import es.upm.oeg.epnoi.matching.metrics.domain.{Metadata, Profile, TopicDistribution}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * Created by cbadenes on 20/04/15.
 */
@RunWith(classOf[JUnitRunner])
class AuthorsSimilarityTest extends FunSuite {

  val metadata = Metadata("title","2012",None)

  test("Same authors") {

    val p1 = Profile(Authors.a1,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))
    val p2 = Profile(Authors.a2,Map((metadata,TopicDistribution(Array(0.3, 0.1)))))
    val p3 = Profile(Authors.a3,Map((metadata,TopicDistribution(Array(0.5, 0.2)))))
    assert(AuthorsSimilarity(Seq(p1,p2,p3),Seq(p3,p2,p1)) == 1.0)
  }


  test("Same distributions and same number of authors") {

    val p1 = Profile(Authors.a1,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))
    val p2 = Profile(Authors.a2,Map((metadata,TopicDistribution(Array(0.3, 0.1)))))
    val p3 = Profile(Authors.a3,Map((metadata,TopicDistribution(Array(0.5, 0.2)))))


    val p4 = Profile(Authors.a4,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))
    val p5 = Profile(Authors.a5,Map((metadata,TopicDistribution(Array(0.3, 0.1)))))
    val p6 = Profile(Authors.a6,Map((metadata,TopicDistribution(Array(0.5, 0.2)))))

    assert(AuthorsSimilarity(Seq(p1,p2,p3),Seq(p4,p5,p6)) == 1.0)
  }


  test("Same distributions and different number of authors") {

    val p1 = Profile(Authors.a1,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))
    val p2 = Profile(Authors.a2,Map((metadata,TopicDistribution(Array(0.3, 0.1)))))
    val p3 = Profile(Authors.a3,Map((metadata,TopicDistribution(Array(0.5, 0.2)))))


    val p4 = Profile(Authors.a4,Map((metadata,TopicDistribution(Array(0.4, 0.2)))))

    assert(AuthorsSimilarity(Seq(p1,p2,p3),Seq(p4)) == 1.0)
  }

}