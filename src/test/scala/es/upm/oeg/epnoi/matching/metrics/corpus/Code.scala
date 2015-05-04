package es.upm.oeg.epnoi.matching.metrics.corpus

import es.upm.oeg.epnoi.matching.metrics.model.Authors
import es.upm.oeg.epnoi.matching.metrics.space.{Metadata, Resource}
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper


object Code {

  def toList(words: Option[Seq[String]]): Seq[String]={
    words match{
      case None => Seq.empty
      case Some(w) => w
    }
  }

  val ro1: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/01",
    url         = "/etc/sample/01",
    metadata    = Metadata("title-01","2012",Some(List(Authors.a3))),
    words       = Some(List("w-01-01","w-01-02")),
    resources   = None)

  val ro2: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/02",
    url         = "/etc/sample/02",
    metadata    = Metadata("title-02","2012",Some(List(Authors.a4,Authors.a1, Authors.a5))),
    words       = Some(List("w-02-01","w-02-02")),
    resources   = None)

  val ro3: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/03",
    url         = "/etc/sample/03",
    metadata    = Metadata("title-03","2012",Some(List(Authors.a4,Authors.a1))),
    words       = Some(List("w-03-01","w-03-02")),
    resources   = None)

  val ro4: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/04",
    url         = "/etc/sample/04",
    metadata    = Metadata("title-04","2012",Some(List(Authors.a4,Authors.a1,Authors.a5))),
    words       = Some(toList(ro2.words).++(toList(ro3.words))),
    resources   = Some(List(ro2,ro3)))


  val corpus = SparkWrapper.sc.parallelize(List(ro1,ro4))

}
