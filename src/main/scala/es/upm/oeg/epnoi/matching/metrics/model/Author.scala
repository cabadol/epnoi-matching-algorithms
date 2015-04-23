package es.upm.oeg.epnoi.matching.metrics.model

/**
 * Created by cbadenes on 23/04/15.
 */
case class Author (id: String) {

  var name: String    = "no-name"

  var surname: String = "no-surname"

  var topicModel: Array[Double] = Array.empty

  

}
