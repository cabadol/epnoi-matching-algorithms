package es.upm.oeg.epnoi.matching.metrics.domain.entity

import java.util

/**
 * Created by cbadenes on 18/06/15.
 */
object ROUtils {


  def toRegularResource(uri: String, url:String, title: String, published: String, authors: util.ArrayList[Author], words: util.ArrayList[String] ): RegularResource ={


    val authorsSeq  = scala.collection.JavaConversions.asScalaBuffer(authors)
    val wordsSeq    = scala.collection.JavaConversions.asScalaBuffer(words)
    val metadata    = Metadata(title,published,authorsSeq)

    return RegularResource(uri,url,metadata,wordsSeq,Seq.empty);

  }

}
