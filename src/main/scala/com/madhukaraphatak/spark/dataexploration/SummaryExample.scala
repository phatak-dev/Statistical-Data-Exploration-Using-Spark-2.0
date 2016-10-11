package com.madhukaraphatak.spark.dataexploration

import org.apache.spark.sql.SparkSession
import LoadUtils._
object SummaryExample {
  def main(args:Array[String]):Unit = {
    val sparkSession = SparkSession.builder()
	    .master("local")
	    .appName("summary example")
            .getOrCreate()
    val lifeExpectancyDF = loadLifeExpectancyData(sparkSession)
      //show summary data of the lifeexpectancy value
   lifeExpectancyDF.describe("LifeExp").show()

   //median value and quantiles
   val medianAndQuantiles = lifeExpectancyDF.stat.approxQuantile("LifeExp",
	   Array(0.25,0.5,0.75),0.0)
   println(medianAndQuantiles.toList)

   //IQR value
   val IQR = medianAndQuantiles(2) - medianAndQuantiles(1)
   println("IQR is" + IQR)
  }
}
