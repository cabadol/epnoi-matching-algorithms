package es.upm.oeg.epnoi.matching.metrics

import java.io._

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper

object PdfToText {


    def main(args: Array[String]) {

      val stripper = new PDFTextStripper
      new File("src/test/resources/paper-corpus").listFiles().
        filter(_.getName.endsWith("pdf")).
        map(file=>(file.getAbsolutePath.replace(".pdf",".txt"),stripper.getText(PDDocument.load(file)))).
        foreach{ case (path,content) =>
          val pw = new PrintWriter(new File(path))
          pw.write(content)
          pw.close
          println(s"$path created!")
        }

    }

}
