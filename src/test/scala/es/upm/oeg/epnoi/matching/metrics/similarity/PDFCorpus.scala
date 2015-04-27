package es.upm.oeg.epnoi.matching.metrics.similarity

import es.upm.oeg.epnoi.matching.metrics.feature.LuceneTokenizer
import es.upm.oeg.epnoi.matching.metrics.model._
import es.upm.oeg.epnoi.matching.metrics.utils.SparkWrapper

/**
 * Created by cbadenes on 24/04/15.
 */
case object PDFCorpus {

  // Research Object 1
  private val ro1Path = "src/test/corpus/ros/r01.pdf"
  val ro1: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/00001",
    metadata    = Metadata("ro.oeg.es/metadata/science/00001",ro1Path,"title-00001","2011",List(Authors.a1,Authors.a2,Authors.a3)),
    terms       = LuceneTokenizer.split(PdfToText.from(ro1Path)),
    topicModel  = None,
    resources   = None)


  // Research Object 2
  private val ro2Path = "src/test/corpus/ros/r02.pdf"
  val ro2: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/00002",
    metadata    = Metadata("ro.oeg.es/metadata/science/00002",ro2Path,"title-00002","2012",List(Authors.a4,Authors.a1)),
    terms       = LuceneTokenizer.split(PdfToText.from(ro2Path)),
    topicModel  = None,
    resources   = None)

  // Research Object 3
  private val ro3Path = "src/test/corpus/ros/r03.pdf"
  private val ro3: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/00003",
    metadata    = Metadata("ro.oeg.es/metadata/science/00003",ro3Path,"title-00003","2010",List(Authors.a8,Authors.a9,Authors.a10)),
    terms       = LuceneTokenizer.split(PdfToText.from(ro3Path)),
    topicModel  = None,
    resources   = None)

  // Research Object 4
  private val ro4Path = "src/test/corpus/ros/r04.pdf"
  val ro4: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/00004",
    metadata    = Metadata("ro.oeg.es/metadata/science/00004",ro4Path,"title-00004","2012",List(Authors.a8,Authors.a5,Authors.a6)),
    terms       = LuceneTokenizer.split(PdfToText.from(ro4Path)),
    topicModel  = None,
    resources   = None)

  // Research Object 5 (aggregated)
  private val ro5Path = "src/test/corpus/ros/r05.zip"
  val ro5: Resource = Resource(
    uri         = "ro.oeg.es/resource/science/00005",
    metadata    = Metadata("ro.oeg.es/metadata/science/00005",ro5Path,"title-00005","2012",List(Authors.a8,Authors.a9,Authors.a10,Authors.a5,Authors.a6)),
    terms       = Seq.empty,
    topicModel  = None,
    resources   = Some(List(ro3,ro4)))


}
