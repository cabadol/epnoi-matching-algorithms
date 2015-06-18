package es.upm.oeg.epnoi.matching.metrics.topics

import es.upm.oeg.epnoi.matching.metrics.domain.entity.TopicDistribution
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * Created by cbadenes on 20/04/15.
 */
@RunWith(classOf[JUnitRunner])
class CaseArrayTest extends FunSuite {

  test("Cosine similarity of two vectors") {

    // Code corpus
    val resources = SparkWrapper.sc.parallelize(0 until 11).map(i=>SampleCaseClass(i,TopicDistribution(Array(0.11359169047386547, 0.40710705057193086, 0.47930125895420367))))
    val cartesian = resources.cartesian(resources).map{case (x1,x2) => (x1,x2,x1.id+x2.id)}
    val grouped = cartesian.sortBy{case (x1,x2,x3) => x1.id}.groupBy(_._1)

    // Conceptual resources
//    val resources = Articles.corpus.map(ConceptualResource(_)).map(SemanticResource(_,TopicDistribution(Array(0.11359169047386547, 0.40710705057193086, 0.47930125895420367))))
//    println(s"${resources.count} resources")
//    val grouped = ROSimilarity.cross(resources)

    println(s"resources: ${resources.count} grouped:${grouped.count}")

    println("Resources: ")
    resources.foreach(r=>println(s"\t$r"))


    println("Grouped: ")
    grouped.foreach(r=>println(s"\t$r"))

    assert(resources.count == grouped.count)
  }


}