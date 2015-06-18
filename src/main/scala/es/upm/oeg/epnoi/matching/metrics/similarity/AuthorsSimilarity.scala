package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.domain.entity.AuthorProfile
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 23/04/15.
 *
 * Measure of similarity between two authors profile or group of authors that measures the Jensen-Shannon Divergence
 * between their topics distribution
 */
object AuthorsSimilarity {


  def apply(p1: RDD[AuthorProfile], p2: RDD[AuthorProfile]): Double = {
    JensenShannonSimilarity(p1.map(_.topics).reduce(_+_).distribution,p2.map(_.topics).reduce(_+_).distribution)
  }

  def apply(p1: Seq[AuthorProfile], p2: Seq[AuthorProfile]): Double = {
    JensenShannonSimilarity(p1.map(_.topics).reduce(_+_).distribution,p2.map(_.topics).reduce(_+_).distribution)
  }

  def apply(a1: AuthorProfile, a2: AuthorProfile): Double = {
    JensenShannonSimilarity(a1.topics.distribution, a2.topics.distribution)
  }


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
