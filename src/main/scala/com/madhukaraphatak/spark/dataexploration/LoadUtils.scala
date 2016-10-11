package com.madhukaraphatak.spark.dataexploration

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
object LoadUtils {
 def loadLifeExpectancyData(sparkSession:SparkSession)= {
  val rawDF = sparkSession.read.format("csv").option("delimiter"," ").
   option("inferSchema","true").load("src/main/resources/LifeExpentancy.txt") 
   //only extract the values we need
   val schema = StructType(Array(
	   StructField("Country",StringType),
	   StructField("LifeExp",DoubleType),
	   StructField("Region",StringType)
   ))
   val selectedDF = rawDF.select("_c0","_c2","_c4")
   val lifeExpectancyDF = sparkSession.createDataFrame(selectedDF.rdd,schema)
   lifeExpectancyDF
 }




}
