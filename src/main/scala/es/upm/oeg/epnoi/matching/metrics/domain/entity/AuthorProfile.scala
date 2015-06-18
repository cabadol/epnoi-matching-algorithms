package es.upm.oeg.epnoi.matching.metrics.domain.entity

/**
 * Map topics distribution and metadata
 * @param author
 * @param publications
 */
case class AuthorProfile (author: Author, publications: Map[Metadata,TopicDistribution])  extends Serializable {

  def +(authorProfile: AuthorProfile): AuthorProfile ={
    if (author != authorProfile.author) throw new IllegalArgumentException(s"Authors are different: ${author.uri} and ${authorProfile.author.uri}")
    AuthorProfile(author, publications ++ authorProfile.publications)
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
