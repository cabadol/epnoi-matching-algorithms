package es.upm.oeg.epnoi.matching.metrics.feature

import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper
import org.apache.spark.mllib.feature.{IDF, HashingTF}
import org.apache.spark.mllib.linalg.{Vectors, Vector}
import org.apache.spark.rdd.RDD
import es.upm.oeg.epnoi.matching.metrics.feature.Tokenizer._
import es.upm.oeg.epnoi.matching.metrics.feature.Featurer._
import scala.collection.mutable

/**
 * Created by cbadenes on 22/04/15.
 */
class FeatureGenerator (directory: String, tokenizer: Tokenizer, featurer: Featurer) extends Serializable{

    // Read corpus
  val corpus: RDD[(String,String)]= SparkWrapper.readCorpus(directory)

  // Map document to index
  val docToKey: collection.Map[String,Long] = corpus.map(_._1).zipWithIndex.collectAsMap()
  val keyToDoc: collection.Map[Long,String] = docToKey.map{case (doc,key) => (key,doc)}


  val tokenized: RDD[(String, Seq[String])] = tokenizer match{
    case Lucene   =>   corpus.mapValues(LuceneTokenizer.split)
    case Default  =>   corpus.mapValues(SimpleTokenizer.split)
  }


  // Count corpus words
  val termCounts: Array[(String, Long)] = WordCounter.flatCount(tokenized.values)

  // Corpus Vocabulary
  val vocabulary: Array[String] = Vocabulary.all(termCounts)

  // Map term -> term index
  val vocabularyMap: Map[String, Int] = vocabulary.zipWithIndex.toMap

  // Feature Vectors
  val featureVectors: RDD[(Long, Vector)] = featurer match{
    case Featurer.TF           => {
      val tf = new HashingTF(numFeatures=1000000)
      tokenized.map{ case (doc,tokens) => (docToKey(doc), tf.transform(tokens))}
    }
    case Featurer.WordCounter  => {
      tokenized.map { case (doc, tokens) =>
        val counts = new mutable.HashMap[Int, Double]()
        tokens.foreach { term =>
          if (vocabularyMap.contains(term)) {
            val idx = vocabularyMap(term)
            counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
          }
        }
        (docToKey(doc), Vectors.sparse(vocabularyMap.size, counts.toSeq))
      }
    }
  }
}
