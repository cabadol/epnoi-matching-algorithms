package es.upm.oeg.epnoi.matching.metrics.feature

import es.upm.oeg.epnoi.matching.metrics.LuceneClassifier

import scala.collection.JavaConverters._

/**
 * Created by cbadenes on 22/04/15.
 */
object LuceneTokenizer {

  def split (line: String): Seq[String] = {
    LuceneClassifier.guessFromString(line).asScala.toList.map(_.getStem).filter(CommonTokenizer.isValid)
  }

}
