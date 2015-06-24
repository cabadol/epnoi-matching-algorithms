package es.upm.oeg.epnoi.matching.metrics.feature

import scala.collection.JavaConverters

/**
 * Created by cbadenes on 22/04/15.
 */
object LuceneTokenizer {

  def apply (text: String): Seq[String] = {
    JavaConverters.asScalaBufferConverter(LuceneClassifier.guessFromString(text)).asScala.toList.map(_.getStem).filter(CommonTokenizer.isValid)
  }

}
