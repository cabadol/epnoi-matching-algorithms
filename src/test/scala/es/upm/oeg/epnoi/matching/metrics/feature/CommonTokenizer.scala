package es.upm.oeg.epnoi.matching.metrics.feature

import java.nio.charset.Charset

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 21/04/15.
 */
object CommonTokenizer {

  def split (line: String): Seq[String] = {
    line.toLowerCase.split("\\s").filter(isValid)
  }

  def isValid (word: String): Boolean ={
    word.length > 2 && !StandardAnalyzer.STOP_WORDS_SET.contains(word) && word.forall(java.lang.Character.isLetter) && word.forall(x=>isEncoded("US-ASCII",x))
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
