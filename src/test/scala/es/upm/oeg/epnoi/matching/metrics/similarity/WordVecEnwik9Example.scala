package es.upm.oeg.epnoi.matching.metrics.similarity

import breeze.linalg.DenseVector
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.mllib.linalg.Vectors


/**
 * Created by cbadenes on 20/07/15.
 */
object WordVecEnwik9Example {



  def main(args: Array[String]): Unit = {

    val modelId = "word2vec/models/word2vect_enwik9_500"

    val input = SparkWrapper.sc.textFile("word2vec/resources/enwik9").map(line => line.split(" ").toSeq)

    val word2vec = new Word2Vec()

    word2vec.setVectorSize(500) // vector dimension (default 100)
//    word2vec.setLearningRate(0) // Initial learning rate (default 0)
//    word2vec.setMinCount(5)     // minimum number of times a token must appear to be included in the vocabulary (default 5)
//    word2vec.setNumIterations(1)// should be smaller than or equal to number of partitions. (default 1)
//    word2vec.setNumPartitions(1)// num partitions (default 1)

    println(s"Word2Vec: $word2vec")


    val model = word2vec.fit(input)


    val synonyms = model.findSynonyms("china", 40)

    for((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }

    // Save and load model
    model.save(SparkWrapper.sc, modelId)

    // Use of existing model
//    val model = Word2VecModel.load(SparkWrapper.sc, modelId)


    // TEST 1: Area result of sum of keywords

    val topicwords = List(
      "science",
      "star",
      "planet",
      "galaxy",
      "survey",
      "study",
      "distance",
      "knowledge",
      "scientific",
      "education",
      "astronomy")


    val vectout = topicwords.map(t1 => DenseVector(model.transform(t1).toArray)).reduce((v1,v2) => v1 + v2)

    println(s"######   Topic for keywords: $topicwords")

    model.findSynonyms(Vectors.dense(vectout.toArray), 20).foreach{case (synonym, cosineSimilarity) => println(s"$synonym $cosineSimilarity")}



    // TEST 2: Analogy

    val king  = DenseVector(model.transform("king").toArray)
    val man   = DenseVector(model.transform("man").toArray)
    val woman = DenseVector(model.transform("woman").toArray)

    val analogyVec = king - man + woman
    println("######   King - Man + Woman: ")

    model.findSynonyms(Vectors.dense(analogyVec.toArray), 20).foreach{case (synonym, cosineSimilarity) => println(s"$synonym $cosineSimilarity")}

  }

}
