package es.upm.oeg.epnoi.matching.metrics.feature

import org.apache.spark.mllib.feature.{IDF, HashingTF}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

/**
 * Created by cbadenes on 20/04/15.
 */
class TFIDFCounter (numFeatures: Integer) extends Serializable {



  def feature( corpus: RDD[(String,String)]) : RDD[(String,Vector)] = {
    // Initialize extractor of alphanumeric non-stop words
    val wordExtractor = new WordExtractor()

    // Compute the TF of 1000 features
    val tf = new HashingTF( numFeatures)
    val tfVectors = corpus.mapValues{ line =>
      tf.transform(wordExtractor.extract(line.replace("\n"," ")))
    }


    tfVectors.foreach( el =>
      println("TF Element: " + el._1 + "|" + el._2)
    )


    // Compute the IDF, then the TF-IDF vectors
    val idf = new IDF()
    val idfModel = idf.fit(tfVectors.values)

    val tfidfVectors = tfVectors.mapValues( tf => idfModel.transform(tf))

    tfidfVectors.foreach( el =>
      println("TFIDF Element: " + el)
    )

    return tfidfVectors

  }

}
