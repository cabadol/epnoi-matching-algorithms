package es.upm.oeg.epnoi.matching.metrics.feature

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 20/04/15.
 */
class WordExtractor extends Serializable {

  /**
   * Read a text document and return a list of words without special characters.
   * Words contained in StandardAnalyzer.STOP_WORDS_SET are removed.
   * @param line
   * @return
   */
  def extract( line: String) : Seq[String] = {
    line.split(" ")
      .map(_.replaceAll("[^a-zA-Z0-9]", ""))
      .filter(word => !StandardAnalyzer.STOP_WORDS_SET.contains(word))
      .toSeq
  }

}
