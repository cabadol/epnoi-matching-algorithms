package es.upm.oeg.epnoi.matching.metrics.feature

import es.upm.oeg.epnoi.matching.metrics.LuceneClassifier

import scala.collection.JavaConverters._

/**
 * Created by cbadenes on 22/04/15.
 */
object LuceneTokenizer {

  def apply (text: String): Seq[String] = {
    LuceneClassifier.guessFromString(text).asScala.toList.map(_.getStem).filter(CommonTokenizer.isValid)
  }

}
