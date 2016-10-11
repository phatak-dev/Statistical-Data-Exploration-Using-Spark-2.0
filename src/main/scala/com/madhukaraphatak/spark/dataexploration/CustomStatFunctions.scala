package com.madhukaraphatak.spark.dataexploration

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row

class CustomStatFunctions(df:Dataset[Row]) {
	def histogram(column:String, buckets:Int) = {
		import df.sparkSession.implicits._
		df.select(column).map(value => value.getDouble(0)).rdd.histogram(buckets)
	}
}
object CustomStatFunctions{
  implicit def addCustomStatFunctions(df:Dataset[Row])= new CustomStatFunctions(df)
}

