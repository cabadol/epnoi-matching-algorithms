package es.upm.oeg.epnoi.matching.metrics.model.space

import es.upm.oeg.epnoi.matching.metrics.algorithm.LDAAlgorithm
import es.upm.oeg.epnoi.matching.metrics.feature.{Concepts, WordCounter}
import es.upm.oeg.epnoi.matching.metrics.model.item._
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD


class SemanticSpace (reprSpace: RepresentationalSpace) extends Serializable {

  // Terms from words of Representational Space
  val vocabulary: Vocabulary = new Vocabulary(Concepts(reprSpace.vocabulary.words))

  // Semantic Resources from resources in Representational Space
  private val semanticResourcesIndexed = reprSpace.resources.map { resource =>
    val terms = resource.words match {
      case None => None
      case Some(words) => Some(Concepts(words))
    }
    val topics = new TopicDistribution(Array.empty[Double])
    SemanticResource(resource, terms, topics)
  }.zipWithIndex.map{ case(value,key)=>(key,value)}

  // TODO TF-IDF feature vectors (using vocabulary, not primitive tfidf
  // Feature vectors based on Term-Frequencies
  val featureVectors: RDD[(Long,Vector)] = semanticResourcesIndexed.map{case (key,semanticResource)=>
    val counter: Vector = semanticResource.terms match{
      case None => Vectors.sparse(vocabulary.size, Seq.empty)
      case Some(words) => WordCounter.count(Concepts(words), vocabulary)
    }
    (key, counter)
  }

  val model = new LDAAlgorithm(featureVectors).model

  // Run algorithm and save topic distributions
  val semanticResources: RDD[SemanticResource] = model.topicDistributions.join(semanticResourcesIndexed).map{ case (key,(topics,semanticResource)) =>
      semanticResource.copy(topics = TopicDistribution(topics.toArray))
  }


}
