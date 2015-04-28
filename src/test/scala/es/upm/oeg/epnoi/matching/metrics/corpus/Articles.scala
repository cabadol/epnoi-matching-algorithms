package es.upm.oeg.epnoi.matching.metrics.corpus

import es.upm.oeg.epnoi.matching.metrics.feature.LuceneTokenizer
import es.upm.oeg.epnoi.matching.metrics.model._
import es.upm.oeg.epnoi.matching.metrics.model.item.{Metadata, Resource}
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper

/**
 * Corpus defined by 4 topics: baseball, motor, religion and space
 */
case object Articles {

  val corpus = SparkWrapper.readCorpus("src/test/corpus/articles").map{x=>
    val name = x._1.substring(x._1.lastIndexOf("/")+1)
    Resource(
      uri         = s"ro.oeg.es/resource/$name",
      url         = x._1,
      metadata    = Metadata(s"title-$name","2011",Some(List(Authors.a1,Authors.a2,Authors.a3))),
      words       = Some(LuceneTokenizer(x._2)),
      resources   = None)
  }

 }
