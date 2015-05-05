package es.upm.oeg.epnoi.matching.metrics.corpus

import es.upm.oeg.epnoi.matching.metrics.feature.LuceneTokenizer
import es.upm.oeg.epnoi.matching.metrics.domain.{Author, Metadata, Resource}
import es.upm.oeg.epnoi.matching.metrics.utils.{PdfToText, SparkWrapper}

/**
 * Created by cbadenes on 24/04/15.
 */
case object Oaipmh {

  val a1: Author = Author("ro.oeg.es/author/000001", "Wing", "Hong", None)
  val a2: Author = Author("ro.oeg.es/author/000002", "Wing", "To", None)
  val a3: Author = Author("ro.oeg.es/author/000003", "Wing", "Keung", None)
  val a4: Author = Author("ro.oeg.es/author/000004", "Ling", "Mei", None)
  val a5: Author = Author("ro.oeg.es/author/000005", "Satya", "Seshavatharam", None)
  val a6: Author = Author("ro.oeg.es/author/000006", "Lakshminarayana", "", None)


  // Research Object 1
  val ro1: Resource = Resource(
    uri = "ro.oeg.es/resource/science/00001",
    url = "src/test/corpus/oaipmh/r01.pdf",
    metadata = Metadata("Discovery of the antigraviton verified by the rotation curve of NGC 6503", "2014", Some(List(a1, a2, a3, a4))),
    words = Some(LuceneTokenizer(PdfToText.from("src/test/corpus/oaipmh/r01.pdf"))),
    resources = None)

  // Research Object 2
  val ro2: Resource = Resource(
    uri = "ro.oeg.es/resource/science/00002",
    url = "src/test/corpus/oaipmh/r02.pdf",
    metadata = Metadata("Black hole Cosmos and the Micro Cosmos", "2013", Some(List(a5, a6))),
    words = Some(LuceneTokenizer(PdfToText.from("src/test/corpus/oaipmh/r02.pdf"))),
    resources = None)


  val corpus = SparkWrapper.sc.parallelize(List(ro1,ro2))

}
