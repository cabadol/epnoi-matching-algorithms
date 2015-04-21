package es.upm.oeg.epnoi.matching.metrics.collaborative

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by cbadenes on 20/04/15.
 */
object ALSExample {

  def main(args: Array[String]) {

    val conf = new SparkConf().setMaster("local").setAppName("ALS Example")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    // Load and parse the data
    val data = sc.textFile("src/test/resources/collaborative-corpus/test1.txt")
    val ratings = data.map(_.split(',') match { case Array(user, item, rate) =>
      Rating(user.toInt, item.toInt, rate.toDouble)
    })

    // Build the recommendation model using ALS
    val rank = 5              // number of latent factors in the model
    val numIterations = 10    // number of iterations to run.
    val lambda = 0.01         // regularization parameter in ALS
    val model = ALS.train(ratings, rank, numIterations, lambda)

    // Evaluate the model on rating data
    val usersProducts = ratings.map { case Rating(user, product, rate) =>
      (user, product)
    }
    val predictions =
      model.predict(usersProducts).map { case Rating(user, product, rate) =>
        ((user, product), rate)
      }
    val ratesAndPreds = ratings.map { case Rating(user, product, rate) =>
      ((user, product), rate)
    }.join(predictions)

    val MSE = ratesAndPreds.map { case ((user, product), (r1, r2)) =>
      val err = (r1 - r2)
      err * err
    }.mean()
    println("Mean Squared Error = " + MSE)

    // Save and load model
    //    model.save(sc, "myModelPath")
    //    val sameModel = MatrixFactorizationModel.load(sc, "myModelPath")

    var myMatrix = Array.ofDim[Double](5,5)

    // build a matrix
    for (i <- 0 to 4) {
      for ( j <- 0 to 4) {
        myMatrix(i)(j) = model.predict(i+1,j+1);
      }
    }

    // Print two dimensional array
    for (i <- 0 to 4) {
      for ( j <- 0 to 4) {
        print(" " + myMatrix(i)(j));
      }
      println();
    }


    //    matrix foreach {
    //      println
    //    }

    println("Completed!!")
  }

}
