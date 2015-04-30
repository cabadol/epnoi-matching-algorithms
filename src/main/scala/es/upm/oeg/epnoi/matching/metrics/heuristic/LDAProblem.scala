package es.upm.oeg.epnoi.matching.metrics.heuristic

import org.uma.jmetal.problem.Problem

import scala.util.Random

/**
 *
 * @param size
 * @param function
 */
class LDAProblem(size: Long, function: Evaluation) extends Problem[LDAParameters]{

  val randomGen = new Random()

  def getNumberOfObjectives = 1

  def getNumberOfConstraints = 2

  def getName = "LDA Parameters Problem"

  def evaluate(s: LDAParameters) ={
    s.setValue(Math.abs(function.evaluate(s)))
    println(s"Evaluating $s")
  }

  def getNumberOfVariables = 3

  def randomTopics() ={
    if (randomGen.nextBoolean()){
      RuleOfThumb(size).toInt
    } else {
      val random = randomGen.nextInt(size.toInt)
      if (random < LDAParameters.minTopics) 1
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
