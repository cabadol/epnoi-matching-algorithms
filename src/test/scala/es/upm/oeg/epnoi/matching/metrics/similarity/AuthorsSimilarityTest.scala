package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.domain.entity.{AuthorProfile, Metadata, TopicDistribution}
import es.upm.oeg.epnoi.matching.metrics.topics.Authors
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * Created by cbadenes on 20/04/15.
 */
@RunWith(classOf[JUnitRunner])
class AuthorsSimilarityTest extends FunSuite {

  val metadata = Metadata("title","2012",Seq.empty)

  test("Same authors") {

    val p1 = AuthorProfile(Authors.a1,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))
    val p2 = AuthorProfile(Authors.a2,Map((metadata,TopicDistribution(Array(0.3, 0.1)))))
    val p3 = AuthorProfile(Authors.a3,Map((metadata,TopicDistribution(Array(0.5, 0.2)))))
    assert(AuthorsSimilarity(Seq(p1,p2,p3),Seq(p3,p2,p1)) > 0.9)
  }


  test("Same distributions, same profiles, different authors") {

    val p1 = AuthorProfile(Authors.a1,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))
    val p2 = AuthorProfile(Authors.a2,Map((metadata,TopicDistribution(Array(0.3, 0.1)))))
    val p3 = AuthorProfile(Authors.a3,Map((metadata,TopicDistribution(Array(0.5, 0.2)))))


    val p4 = AuthorProfile(Authors.a4,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))
    val p5 = AuthorProfile(Authors.a5,Map((metadata,TopicDistribution(Array(0.3, 0.1)))))
    val p6 = AuthorProfile(Authors.a6,Map((metadata,TopicDistribution(Array(0.5, 0.2)))))

    assert(AuthorsSimilarity(Seq(p1,p2,p3),Seq(p4,p5,p6)) > 0.9)
  }


  test("Only one same distribution, different authors") {

    val p1 = AuthorProfile(Authors.a1,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))
    val p2 = AuthorProfile(Authors.a2,Map((metadata,TopicDistribution(Array(0.3, 0.1)))))
    val p3 = AuthorProfile(Authors.a3,Map((metadata,TopicDistribution(Array(0.5, 0.2)))))


    val p4 = AuthorProfile(Authors.a4,Map((metadata,TopicDistribution(Array(0.4, 0.3)))))

    assert(AuthorsSimilarity(Seq(p1,p2,p3),Seq(p4)) > 0.9)
  }

}