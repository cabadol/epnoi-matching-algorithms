package es.upm.oeg.epnoi.matching.metrics.feature

import java.nio.charset.Charset

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 21/04/15.
 */
object Tokenizer {

  /**
   * Read lines of text and return a list of words without special characters.
   * Words contained in StandardAnalyzer.STOP_WORDS_SET are removed.
   * @param corpus
   * @return
   */
  def split (corpus: RDD[String]): RDD[Seq[String]] ={
    printAll(corpus.
      map(_.toLowerCase.split("\\s")).                                  // split by whitespaces, tab, new_lines..
      map(_.
       filter(_.length > 4).                                             // filter short words
       filter(!StandardAnalyzer.STOP_WORDS_SET.contains(_)).             // filter non stop words: a, an, the...
       filter(_.forall(java.lang.Character.isLetter)).                   // filter alphabetic
       filter(_.forall(c=>isEncoded("US-ASCII",c))))                     // filter charset
    )
  }

  def isEncoded (charset: String, letter: Char): Boolean ={
    Charset.forName(charset).newEncoder().canEncode(letter)
  }

  def printAll(tokens: RDD[Seq[String]]): RDD[Seq[String]] ={
    println("*"*20+" Tokenizer.split:")
    tokens.collect().foreach(x => println(s"·$x"))
    return tokens
  }


}
