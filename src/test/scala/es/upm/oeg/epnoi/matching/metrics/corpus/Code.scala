package es.upm.oeg.epnoi.matching.metrics.corpus

import es.upm.oeg.epnoi.matching.metrics.domain.entity.{Metadata, RegularResource}
import es.upm.oeg.epnoi.matching.metrics.feature.LuceneTokenizer
import es.upm.oeg.epnoi.matching.metrics.topics.Authors
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper


object Code {

  def toList(words: Option[Seq[String]]): Seq[String]={
    words match{
      case None => Seq.empty
      case Some(w) => w
    }
  }

  val ro1: RegularResource = RegularResource(
    uri         = "ro.oeg.es/resource/science/baseball01",
    url         = "src/test/corpus/articles/baseball01.txt",
    metadata    = Metadata("Baseball-01","2011",List(Authors.a3, Authors.a2)),
    bagOfWords  = LuceneTokenizer(scala.io.Source.fromFile("src/test/corpus/articles/baseball01.txt").mkString),
    resources   = Seq.empty)

  val ro2: RegularResource = RegularResource(
    uri         = "ro.oeg.es/resource/science/motor05",
    url         = "src/test/corpus/articles/motor05.txt",
    metadata    = Metadata("Motor-05","2013",List(Authors.a4,Authors.a1, Authors.a5)),
    bagOfWords  = LuceneTokenizer(scala.io.Source.fromFile("src/test/corpus/articles/motor05.txt").mkString),
    resources   = Seq.empty)

  val ro3: RegularResource = RegularResource(
    uri         = "ro.oeg.es/resource/science/motor03",
    url         = "src/test/corpus/articles/motor03.txt",
    metadata    = Metadata("Motor-03","2012",List(Authors.a4,Authors.a1)),
    bagOfWords  = LuceneTokenizer(scala.io.Source.fromFile("src/test/corpus/articles/motor03.txt").mkString),
    resources   = Seq.empty)

  val ro4: RegularResource = RegularResource(
    uri         = "ro.oeg.es/resource/science/motor00",
    url         = "src/test/corpus/articles/motor00.zip",
    metadata    = Metadata("Motor-Group","2013",List(Authors.a4,Authors.a1,Authors.a5)),
    bagOfWords  = ro2.bagOfWords.++(ro3.bagOfWords),
    resources   = List(ro2,ro3))

  val ro5: RegularResource = RegularResource(
    uri         = "ro.oeg.es/resource/science/baseball02",
    url         = "src/test/corpus/articles/baseball02.txt",
    metadata    = Metadata("Baseball-02","2012",List(Authors.a3, Authors.a2)),
    bagOfWords  = LuceneTokenizer(scala.io.Source.fromFile("src/test/corpus/articles/baseball02.txt").mkString),
    resources   = Seq.empty)

  val ro6: RegularResource = RegularResource(
    uri         = "ro.oeg.es/resource/science/religion01",
    url         = "src/test/corpus/articles/religion01.txt",
    metadata    = Metadata("Religion-01","2015",List(Authors.a3, Authors.a4)),
    bagOfWords  = LuceneTokenizer(scala.io.Source.fromFile("src/test/corpus/articles/religion01.txt").mkString),
    resources   = Seq.empty)

  val corpus = SparkWrapper.sc.parallelize(List(ro1,ro4,ro5,ro6))

}
