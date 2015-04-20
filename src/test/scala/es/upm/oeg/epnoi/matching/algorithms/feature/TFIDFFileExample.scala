package es.upm.oeg.epnoi.matching.algorithms.feature

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by cbadenes on 20/04/15.
 */
object TFIDFFileExample {


  def main(args: Array[String]): Unit = {
    /***********************************
      * Spark Context
      ***********************************/
    println("Starting...")
    val conf = new SparkConf().setMaster("local").setAppName("TFIDF Example")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)


    /***********************************
      * Feature Extraction: TF-IDF
      ***********************************/
    // Load a directory
    println("Reading file ")
    val input = sc.textFile("src/test/resources/tfidf/test1.txt")

    // Each document is split into words
    val wordExtractor = new WordExtractor()
    val wordVectors = input.map{ line =>
      wordExtractor.extract(line)
    }

    // Create a HashingTF instance to map document text to vectors of 1000 features
    val tf = new HashingTF( numFeatures=1000)
    println("HashingTF initialize with  " + tf.numFeatures + " features")


    //Each word is mapped to one feature
    val tfVectors = tf.transform(wordVectors).cache()
    tfVectors.foreach( el =>
      println("TF Element: " + el)
    )


    // Compute the IDF, then the TF-IDF vectors
    val idf = new IDF()
    val idfModel = idf.fit(tfVectors)
    val tfidfVectors = idfModel.transform(tfVectors)


    tfidfVectors.foreach( el =>
      println("TFIDF Element: " + el)
    )



  }

}
