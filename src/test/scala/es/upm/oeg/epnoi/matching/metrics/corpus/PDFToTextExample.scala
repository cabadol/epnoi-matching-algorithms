package es.upm.oeg.epnoi.matching.metrics.corpus

import java.io.{IOException, PrintWriter, File}

import es.upm.oeg.epnoi.matching.metrics.feature.PdfToText

/**
 * Created by cbadenes on 20/07/15.
 */
object PDFToTextExample {

  def findDirectories(path: File): List[File]  =
    path :: path.listFiles.filter {
      _.isDirectory
    }.toList.flatMap {
      findDirectories(_)
    }


  def main(args: Array[String]): Unit = {

    val directories = findDirectories(new File("/Users/cbadenes/Documents/Academic/MUIA/TFM/ressist/resources/publications/oaipmh/"))
    val files       = directories.flatMap(f=>f.listFiles.filter{_.getName.endsWith("pdf")})

    files.foreach{ case file =>

      val pw = new PrintWriter(new File(file.getAbsolutePath.replace(".pdf",".txt")))
      try {
        pw.write(PdfToText.from(file.getAbsolutePath))
        pw.close
        println(s"Pdf $file processed")
      } catch {
        case ioe: IOException => pw.close()
        case e: Exception => pw.close()
      }

    }
  }

}
