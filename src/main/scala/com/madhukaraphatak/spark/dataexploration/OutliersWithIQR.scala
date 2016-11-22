package com.madhukaraphatak.spark.dataexploration

import org.apache.spark.sql._
import org.apache.spark.sql.types._

object OutliersWithIQR {
  def main(args:Array[String])= {
  val sparkSession = SparkSession.builder()
	    .master("local")
	    .appName("summary example")
            .getOrCreate()

   //create sample data 
   val sampleData = List(10.2, 14.1,14.4,14.4,14.4,14.5,14.5,14.6,14.7,  
	   14.7, 14.7,14.9,15.1, 15.9,16.4)
   val rowRDD = sparkSession.sparkContext.makeRDD(sampleData.map(value => Row(value)))
   val schema = StructType(Array(StructField("value",DoubleType)))
   val df = sparkSession.createDataFrame(rowRDD,schema)

   // calculate quantiles and IQR
   val quantiles = df.stat.approxQuantile("value",
	   Array(0.25,0.75),0.0)
   val Q1 = quantiles(0)
   val Q3 = quantiles(1)
   val IQR = Q3 - Q1

   val lowerRange = Q1 - 1.5*IQR
   val upperRange = Q3+ 1.5*IQR

   val outliers = df.filter(s"value < $lowerRange or value > $upperRange") 
   outliers.show()
   sparkSession.stop()
  }

}
