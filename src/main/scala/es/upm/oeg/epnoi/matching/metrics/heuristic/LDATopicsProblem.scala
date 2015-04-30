package es.upm.oeg.epnoi.matching.metrics.heuristic

import org.apache.commons.math3.distribution.NormalDistribution
import org.uma.jmetal.problem.Problem

import scala.util.Random

/**
 *
 * @param size
 * @param function
 */
class LDATopicsProblem(size: Long, function: Evaluation) extends Problem[LDAParameters]{

  val randomGen = new Random()

  def getNumberOfObjectives = 3

  def getNumberOfConstraints = 2

  def getName = "LDA Parameters Problem"

  val topicDist = new NormalDistribution(Math.sqrt(size/2).toInt,2)

  def evaluate(s: LDAParameters) ={
    val result = function.evaluate(s)
    // Objetive1 :: Minimize the abs value of logLikelihood => Maximize Likelihood
    s.setLoglikelihood(Math.abs(result.o1))
    // Objetive2 :: Minimize the abs value of logPrior      => Maximize Prior
    s.setLogprior(Math.abs(result.o2))
    // Objetive3 :: Minimize the inverse of Normal Distribution with mean in the Rule of Thumb => Maximize number of cluster close to Rule of Thumb
    s.setTopicsObj( 1 - topicDist.density(result.o3))      
  }

  def getNumberOfVariables = 3

  def randomTopics() ={
    if (randomGen.nextBoolean()){
      // Rule of thumb
      Math.sqrt(size/2).toInt
    } else {
      val random = randomGen.nextInt(size.toInt+1)
      if (random < LDAParameters.minTopics) size.toInt
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
    val alpha = randomConcentration(LDAParameters.minAlpha, LDAParameters.maxAlpha)
    val beta = randomConcentration(LDAParameters.minBeta, LDAParameters.maxBeta)
    val solution = new LDAParameters(numTopics,alpha,beta,10,size.toInt)
    println(s"Solution: $solution")
    solution
  }
}
