package es.upm.oeg.epnoi.matching.algorithms.topics

import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 20/04/15.
 */
object LDAAlgorithm extends Serializable{


  def calculate( numTopics: Integer, vectors : RDD[(String,Vector)]): DistributedLDAModel ={
    return new LDA().
      setK(numTopics).
      setMaxIterations(10).
      run(vectors.values.zipWithIndex.map(_.swap))
  }

}
