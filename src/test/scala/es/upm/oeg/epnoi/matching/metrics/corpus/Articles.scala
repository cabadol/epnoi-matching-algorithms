package es.upm.oeg.epnoi.matching.metrics.corpus

import es.upm.oeg.epnoi.matching.metrics.domain.entity.{Metadata, RegularResource}
import es.upm.oeg.epnoi.matching.metrics.feature.LuceneTokenizer
import es.upm.oeg.epnoi.matching.metrics.topics._
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper

/**
 * Corpus defined by 4 topics: baseball, motor, religion and space
 */
case object Articles {

  val metadatas: Map[String,Metadata] = Map(
    ("baseball01.txt",Metadata("baseball01","2011",Some(List(Authors.a1,Authors.a2,Authors.a3)))),
    ("baseball02.txt",Metadata("baseball01","2012",Some(List(Authors.a1,Authors.a2,Authors.a3)))),
    ("baseball03.txt",Metadata("baseball01","2013",Some(List(Authors.a1,Authors.a2,Authors.a3)))),
    ("baseball04.txt",Metadata("baseball01","2014",Some(List(Authors.a1,Authors.a2,Authors.a3)))),
    ("baseball05.txt",Metadata("baseball01","2015",Some(List(Authors.a1,Authors.a2,Authors.a3)))),
    ("motor01.txt",Metadata("motor01","2011",Some(List(Authors.a4,Authors.a5)))),
    ("motor02.txt",Metadata("motor02","2012",Some(List(Authors.a4,Authors.a5)))),
    ("motor03.txt",Metadata("motor03","2013",Some(List(Authors.a4,Authors.a5)))),
    ("motor04.txt",Metadata("motor04","2014",Some(List(Authors.a4,Authors.a5)))),
    ("motor05.txt",Metadata("motor05","2015",Some(List(Authors.a4,Authors.a5)))),
    ("religion01.txt",Metadata("religion01","2011",Some(List(Authors.a6,Authors.a7,Authors.a8)))),
    ("religion02.txt",Metadata("religion02","2012",Some(List(Authors.a6,Authors.a7,Authors.a8)))),
    ("religion03.txt",Metadata("religion03","2013",Some(List(Authors.a6,Authors.a7,Authors.a8)))),
    ("religion04.txt",Metadata("religion04","2014",Some(List(Authors.a6,Authors.a7,Authors.a8)))),
    ("religion05.txt",Metadata("religion05","2015",Some(List(Authors.a6,Authors.a7,Authors.a8)))),
    ("space01.txt",Metadata("space01","2011",Some(List(Authors.a8,Authors.a9)))),
    ("space02.txt",Metadata("space02","2012",Some(List(Authors.a8,Authors.a9)))),
    ("space03.txt",Metadata("space03","2013",Some(List(Authors.a8,Authors.a9)))),
    ("space04.txt",Metadata("space04","2014",Some(List(Authors.a8,Authors.a9)))),
    ("space05.txt",Metadata("space05","2015",Some(List(Authors.a8,Authors.a9))))
  ).withDefaultValue(Metadata("hockey","2015",Some(List(Authors.a10))))


  val corpus = SparkWrapper.readCorpus("src/test/corpus/articles/*").map{x=>
    val name = x._1.substring(x._1.lastIndexOf("/")+1)
    RegularResource(
      uri         = s"ro.oeg.es/resource/$name",
      url         = x._1,
      metadata    = metadatas(name),
      words       = Some(LuceneTokenizer(x._2)),
      resources   = None)
  }

 }
