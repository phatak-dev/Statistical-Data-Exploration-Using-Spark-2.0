package com.madhukaraphatak.spark.dataexploration

import org.apache.spark.sql.SparkSession
import LoadUtils._
import CustomStatFunctions._
object ShapeOfDataExample {
  def main(args:Array[String]):Unit = {
    val sparkSession = SparkSession.builder()
	    .master("local")
	    .appName("summary example")
            .getOrCreate()
    val lifeExpectancyDF = loadLifeExpectancyData(sparkSession)
    val histogram = lifeExpectancyDF.histogram("LifeExp",5)
    println(histogram)
  }
}
