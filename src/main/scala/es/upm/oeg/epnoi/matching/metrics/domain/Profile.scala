package es.upm.oeg.epnoi.matching.metrics.domain

/**
 *
 * @param author
 */
case class Profile (author: Author, publications: Map[Metadata,TopicDistribution])  extends Serializable {

  def +(profile: Profile): Profile ={
    if (author != profile.author) throw new IllegalArgumentException(s"Authors are different: ${author.uri} and ${profile.author.uri}")
    Profile(author, publications ++ profile.publications)
  }


  /**
   * Accumulative Topic Distribution based on publications
   * @return
   */
  def topics(): TopicDistribution ={
    publications.values.reduce(_.+(_))
  }

  //TODO Implements a temporal topic distribution
  def topics(range: Range): TopicDistribution={
    TopicDistribution(Array.empty[Double])
  }

  override def toString(): String={
    s"$author|$topics|Publications:$publications"
  }

}
