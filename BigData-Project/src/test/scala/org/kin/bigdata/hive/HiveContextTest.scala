package org.kin.bigdata.hive

import org.apache.spark.sql.SparkSession

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by 健勤 on 2017/8/6.
 */
object HiveContextTest {
  def main(args: Array[String]) {
    val sparkSession = SparkSession.builder().enableHiveSupport().appName("HiveContextTest").master("local[2]").getOrCreate()

    val movies = sparkSession.sql("select * from movie_info")
    movies.foreach(println(_))

    sparkSession.stop()
  }
}
