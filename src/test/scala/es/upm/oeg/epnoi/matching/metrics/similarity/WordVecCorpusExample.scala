package es.upm.oeg.epnoi.matching.metrics.similarity

import breeze.linalg.DenseVector
import es.upm.oeg.epnoi.matching.metrics.feature.LuceneTokenizer
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.mllib.linalg
import org.apache.spark.mllib.linalg.Vectors


/**
 * Created by cbadenes on 20/07/15.
 */
object WordVecCorpusExample {


//  def createModel(path: String): Word2VecModel={
//    val input = SparkWrapper
//      .readCorpus(path)
//      .map{case (f,x)=>LuceneTokenizer(x)}
//    val word2vec = new Word2Vec()
//    val model = word2vec.fit(input)
//    model.save(SparkWrapper.sc, path)
//    return model
//  }
//
//  def loadModel(path: String): Word2VecModel={
//    return Word2VecModel.load(SparkWrapper.sc,path)
//  }

  def loadModel(path: String): Word2VecModel={
    try{
      return Word2VecModel.load(SparkWrapper.sc,path)
    } catch{
      case e: Exception =>{
        val input = SparkWrapper
          .readCorpus(path)
          .map{case (f,x)=>LuceneTokenizer(x)}
        val word2vec = new Word2Vec()
        val model = word2vec.fit(input)
        model.save(SparkWrapper.sc, path)
        return model
      }
    }
  }


  def main(args: Array[String]): Unit = {

    val path = "/Users/cbadenes/Documents/Academic/MUIA/TFM/ressist/resources/publications/oaipmh/**/**/*.txt"
    val model = loadModel(path)


//    val patient : linalg.Vector  = model.transform("patient")
//    val man : linalg.Vector      = model.transform("man")
//
//    val bv1 = new DenseVector(patient.toArray)
//    val bv2 = new DenseVector(man.toArray)
//
//    val vectout = Vectors.dense((bv1 + bv2).toArray)

model.getVectors.map(y => y._1).foreach{ case word =>
  print(s"$word,")
}

//    model.getVectors.foreach{ case v =>
//
//      println( v._1 + " -> " + v._2.toList )
//
//    }

//    val synonyms = model.findSynonyms(vectout,20)
//
//    for ((synonym, cosineSimilarity) <- synonyms){
//      println(s"$synonym $cosineSimilarity")
//    }



  }

}
