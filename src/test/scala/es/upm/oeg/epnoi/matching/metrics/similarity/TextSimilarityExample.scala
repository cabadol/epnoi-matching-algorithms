package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.feature._

/**
 * Created by cbadenes on 20/04/15.
 */
object TextSimilarityExample {

  def main(args: Array[String]): Unit = {

    // Extract features from corpus
    println("extracting features from corpus..")
    val generator = new FeatureGenerator("src/test/corpus/articles", Tokenizer.Lucene, Featurer.WordCounter)

    // Get text similarity
    println("learning a topic model..")
    val simText = new TextSimilarity(generator.featureVectors)
    println("completed!")

    // Print log-likelihood
    println("*"*20+" Model:")
    println("Number of topics: \t" + simText.lda.getK)
    println("Alpha: \t" + simText.lda.getAlpha)
    println("Beta: \t" + simText.lda.getBeta)
    println("Checkpoint Interval: \t" + simText.lda.getCheckpointInterval)
    println("Doc Concentration: \t" + simText.lda.getDocConcentration)
    println("Max Iterations: \t" + simText.lda.getMaxIterations)
    println("Topic Concentration: \t" + simText.lda.getTopicConcentration)
    println("Seed: \t" + simText.lda.getSeed)
    println("LogLikelihood: \t"+ simText.model.logLikelihood)


    // Print topics, showing top-weighted 10 terms for each topic.
    println("*"*20+" Topics:")
    simText.model.describeTopics(maxTermsPerTopic = 10).foreach { case (terms, termWeights) =>
      println("TOPIC:")
      terms.zip(termWeights).foreach { case (term, weight) =>
        println(s" ·${generator.vocabulary(term.toInt)}\t$weight")
      }
      println()
    }

    // Print topics in documents
    println("*"*20+" Topics in document:")
    simText.model.topicDistributions.foreach{x =>
      println(generator.keyToDoc(x._1)+":\t"+x._2)
    }

    // Print similarity matrix
    println("*"*20+" Similarity between documents:")
    simText.matrix.collect.foreach{x =>
      println(s"${generator.keyToDoc(x._1)}")
      x._2.foreach(tuple=>println(s"\t${generator.keyToDoc(tuple._2)} \t: ${{tuple._3}%1.5f}"))
    }

    // Print documents as vector of tokens
    println("*"*20+" Tokens:")
    generator.tokenized.collect.foreach(x=>println(s"$x"))

    // Print Vocabulary
    println("*"*20+" Vocabulary:")
    generator.vocabulary.foreach(x=>print(s"$x,"))


  }


}
