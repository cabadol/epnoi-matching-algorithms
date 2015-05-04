package es.upm.oeg.epnoi.matching.metrics.model.search

import org.apache.commons.math3.distribution.NormalDistribution
import org.apache.spark.mllib.clustering.LDA
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import org.uma.jmetal.problem.Problem

import scala.util.Random


class LDAProblem(domain: RDD[(Long, Vector)]) extends Problem[LDASolution]{

  val randomGen = new Random()

  val numLDAIterations = 10

  def getNumberOfObjectives = 3

  def getNumberOfConstraints = 2

  def getName = "LDA Parameters Problem"

  val domainSize = domain.count

  val topicDist = new NormalDistribution(Math.sqrt(domainSize/2).toInt,2) // Normal distribution around Rule of Thumb

  def evaluate(solution: LDASolution) ={

    val model = new LDA().
      setK(solution.getTopics).
      setMaxIterations(solution.getMaxIterations).
      setDocConcentration(solution.getAlpha).
      setTopicConcentration(solution.getBeta).
      run(domain)
    println("----------------------------")
    println(s"Value of solution: $solution")
    println(s"loglikelihood: " + model.logLikelihood)
    println(s"logprior: " + model.logPrior)


    // Objetive1 :: Minimize the abs value of logLikelihood => Maximize Likelihood
    solution.setLoglikelihood(Math.abs(model.logLikelihood))
    // Objetive2 :: Minimize the abs value of logPrior      => Maximize Prior
    solution.setLogprior(Math.abs(model.logPrior))
    // Objetive3 :: Minimize the inverse of Normal Distribution with mean in the Rule of Thumb => Maximize number of cluster close to Rule of Thumb
    solution.setTopicsObj( 1 - topicDist.density(model.k))
  }

  def getNumberOfVariables = 3

  def randomTopics() ={
    if (randomGen.nextBoolean()){
      // Rule of thumb
      Math.sqrt(domainSize/2).toInt
    } else {
      val random = randomGen.nextInt(domainSize.toInt+1)
      if (random < LDASolution.minTopics) domainSize.toInt
      else random
    }
  }

  def randomConcentration(min: Double, max: Double) ={
    val random = randomGen.nextInt(max.toInt)
    if (random < min) min
    else random.toDouble
  }

  def createSolution() = {
    val numTopics = randomTopics()
    val alpha = randomConcentration(LDASolution.minAlpha, LDASolution.maxAlpha)
    val beta = randomConcentration(LDASolution.minBeta, LDASolution.maxBeta)
    val solution = new LDASolution(numTopics,alpha,beta,numLDAIterations,domainSize.toInt)
    println(s"Solution: $solution")
    solution
  }
}
