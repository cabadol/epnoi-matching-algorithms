package es.upm.oeg.epnoi.matching.metrics.topics.search

import org.apache.commons.math3.distribution.NormalDistribution
import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory
import org.uma.jmetal.problem.Problem

import scala.util.Random


class LDAProblem(domain: RDD[(Long, Vector)], iterations: Integer) extends Problem[LDASolution]{

  val log = LoggerFactory.getLogger(classOf[LDAProblem]);

  val randomGen = new Random()

  val numLDAIterations = iterations

  def getNumberOfObjectives = 1

  def getNumberOfConstraints = 2

  def getName = "LDA Parameters Problem"

  val domainSize = domain.count

  val topicDist = new NormalDistribution(Math.sqrt(domainSize/2).toInt,2) // Normal distribution around rule of thumb

  var values : scala.collection.mutable.Map[LDASolution,DistributedLDAModel] = scala.collection.mutable.Map[LDASolution,DistributedLDAModel]()

  def evaluate(solution: LDASolution) ={

    var model: DistributedLDAModel = null

    solution.setLoglikelihood(0.0)
    solution.setLogprior(0.0)
    solution.setTopicsObj(0.0)
    solution.setDistribution(0.0)

    if (values.contains(solution)){
      model = values(solution)
    }else{
      model = new LDA().
        setK(solution.getTopics).
        setMaxIterations(iterations).
        setDocConcentration(solution.getAlpha).
        setTopicConcentration(solution.getBeta).
        run(domain)
      values(solution) = model
    }

    // Objetive1 :: Minimize the abs value of logLikelihood => Maximize Likelihood
    solution.setLoglikelihood(Math.abs(model.logLikelihood))
    // Objetive2 :: Minimize the abs value of logPrior      => Maximize Prior
    solution.setLogprior(Math.abs(model.logPrior))
    // Objetive3 :: Minimize the inverse of Normal Distribution with mean in the Rule of Thumb => Maximize number of cluster close to Rule of Thumb
    solution.setTopicsObj( 1 / (topicDist.density(model.k)+0.1))
    // Objetive4 :: Minimize the inverse of distance between alpha and beta  => Maximize distance between alpha and beta
    solution.setDistribution(1 / (Math.abs(solution.getAlpha - solution.getBeta)+0.1))

    log.info("----------------------------")
    log.info(s"LDA Solution: \t$solution")
  }

  def getNumberOfVariables = 3

  def randomInterval(min: Double, max: Double):Double ={
    min + randomGen.nextInt( (max - min).toInt + 1 )
  }

  def createSolution() = {
    val numTopics = randomInterval(1.0,Math.sqrt(domainSize/2)*2).toInt
    val alpha = randomInterval(LDASolution.minAlpha, LDASolution.maxAlpha)
    val beta = randomInterval(LDASolution.minBeta, LDASolution.maxBeta)
    val solution = new LDASolution(numTopics,alpha,beta,numLDAIterations,domainSize.toInt)
    log.info(s"Solution: $solution")
    solution
  }
}
