package es.upm.oeg.epnoi.matching.metrics.feature

import java.io._

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.slf4j.LoggerFactory

/**
 *
 */
object PdfToText {

    val log = LoggerFactory.getLogger(PdfToText.getClass);

    def toFiles(directory: String) {

      val stripper = new PDFTextStripper
      new File(directory).listFiles().
        filter(_.getName.endsWith("pdf")).
        map(file=>(file.getAbsolutePath.replace(".pdf",".txt"),stripper.getText(PDDocument.load(file)))).
        foreach{ case (path,content) =>
          val pw = new PrintWriter(new File(path))
          pw.write(content)
          pw.close
          log.info(s"$path created!")
        }

    }


    def from (path: String): String ={
      new PDFTextStripper("utf-8").getText(PDDocument.load(path))
    }

}
