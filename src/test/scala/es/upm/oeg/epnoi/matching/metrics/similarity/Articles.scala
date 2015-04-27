package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.feature.LuceneTokenizer
import es.upm.oeg.epnoi.matching.metrics.model._
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper

/**
 * Corpus defined by 4 topics: baseball, motor, religion and space
 */
case object Articles {

  val corpus = SparkWrapper.readCorpus("src/test/corpus/articles").map{x=>
    val name = x._1.substring(x._1.lastIndexOf("/")+1)
    Resource(
      uri         = s"ro.oeg.es/resource/$name",
      metadata    = Metadata(s"ro.oeg.es/metadata/$name",x._1,s"title-$name","2011",List(Authors.a1,Authors.a2,Authors.a3)),
      terms       = LuceneTokenizer.split(x._2),
      topicModel  = None,
      resources   = None)
  }

 }
