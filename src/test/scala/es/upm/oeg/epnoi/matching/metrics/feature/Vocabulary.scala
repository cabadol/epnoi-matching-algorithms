package es.upm.oeg.epnoi.matching.metrics.feature

/**
 * Created by cbadenes on 21/04/15.
 */
object Vocabulary {

  def all( wordCounts: Array[(String, Long)]): Array[String] ={
    printAll("all",wordCounts.map(_._1))
  }

  def lessCommon( ratio: Integer,  wordCounts: Array[(String, Long)]): Array[String] ={
    printAll("lessCommon", wordCounts.takeRight(wordCounts.size - (ratio * wordCounts.size / 100)).map(_._1))
  }

  def printAll( method: String, vocabulary: Array[String]): Array[String] ={
    println("*"*20+" Vocabulary."+method+":")
    vocabulary.foreach(x=>print(s"$x,"))
    return vocabulary
  }


}
