package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.domain.Profile
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 23/04/15.
 */
object AuthorsSimilarity {


  def apply(p1: RDD[Profile], p2: RDD[Profile]): Double = {
    JensenShannonSimilarity(p1.map(_.topics).reduce(_+_).distribution,p2.map(_.topics).reduce(_+_).distribution)
  }

  def apply(p1: Seq[Profile], p2: Seq[Profile]): Double = {
    JensenShannonSimilarity(p1.map(_.topics).reduce(_+_).distribution,p2.map(_.topics).reduce(_+_).distribution)
  }

  def apply(p1: Profile, p2: Profile): Double = {
    JensenShannonSimilarity(p1.topics.distribution, p2.topics.distribution)
  }


  //  /**
  //   * Jensen-Shannon Divergence-based Similarity between topic models of each author
  //   * @param a1
  //   * @param a2
  //   */
  //  def between(a1: Author, a2: Author): Unit ={
  //    JensenShannonSimilarity.between(a1.topicModel, a2.topicModel)
  //  }
  //
  //  /**
  //   * Jensen-Shannon Divergence-based Similarity between Bernoulli joint-distribution from topic models of each group of authors
  //   * @param g1
  //   * @param g2
  //   */
  //  def between(g1: List[Author], g2: List[Author]): Unit ={
  //    def joint (m1: Array[Double],m2: Array[Double]) = Bernoulli.joint(m1,m2)
  //    JensenShannonSimilarity.between(g1.map(_.topicModel).reduce(joint),g2.map(_.topicModel).reduce(joint))
  //  }
  //
  //  /**
  //   * Diachronic similarity
  //   * @param a1
  //   * @param a2
  //   * @param timeframe
  //   */
  //  def between(a1: Author, a2: Author, timeframe: Range): Unit ={
  //
  //    // step 1a: Obtain resources of a1 in 'timeframe'
  //
  //    // step 2a: Bernoulli between these resources
  //
  //    // step 1b: Obtain resources of b1 in 'timeframe'
  //
  //    // step 2b: Bernoulli between these resources
  //
  //    // jensen-shannon similarity between these distributions
  //
  //    //TODO Implements
  //  }
  //
  //  /**
  //   * Diachronic similarity
  //   * @param g1
  //   * @param g2
  //   * @param timeframe
  //   */
  //  def between(g1: List[Author], g2: List[Author], timeframe: Range): Unit ={
  //    //TODO Implements
  //  }


}
