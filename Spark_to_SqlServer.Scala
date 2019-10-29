// Spark 2.0 to SQL Server via External Data Source API and SQL JDBC
// ./bin/spark --master yarn spark://$SPARK-MASTER$:7077 --jars /$location$/mssql-jdbc-7.4.1.jre8.jar

import java.util.Properties
import org.apache.log4j.Logger
import org.apache.log4j.Level

import com.microsoft.sqlserver.jdbc
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SQLContext



import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql._
import org.apache.spark.sql.types.{StructType, StructField, StringType, IntegerType,DoubleType,ByteType};

import org.apache.spark.sql.functions._
import org.apache.spark.storage.StorageLevel


// Option 1: Build the parameters into a JDBC url to pass into the Spark DataFrame APIs
val jdbcUsername = "$USER_NAME$" //yaswanth
val jdbcPassword = "$PASSWORD$"  //******
val jdbcHostname = "$HOST_NAME$" //***********
val jdbcPort = 1433
val jdbcDatabase ="$DATABASE_NAME$" //test
val driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
val jdbcUrl = s"jdbc:sqlserver://${jdbcHostname}:${jdbcPort};database=${jdbcDatabase};user=${jdbcUsername};password=${jdbcPassword}"

// Option 2: Create a Properties() object to hold the parameters.
val connectionProperties = new Properties()
connectionProperties.put("user", "$USER_NAME$")
connectionProperties.put("password", "$PASSWORD$")
connectionProperties.setProperty("Driver", driverClass)

//Option 3: Creating DataFrame and reading data from Sql Server JDBC
//val sqlserver_table = spark.read.jdbc(jdbc, "dbo.table_name", connectionProperties)
// val jdbc = s"jdbc:sqlserver://${jdbcHostname}:1433;database=${jdbcDatabase}"

val jdbcDF2 = spark.read.jdbc("jdbc:sqlserver://******:1433;database=test","dbo.table_name",connectionProperties)

//Option 4 Writing Data in SqlServer

 jdbcDF2.write.mode(SaveMode.Append) /*<--- Append to the existing table*/.jdbc("jdbc:sqlserver://******:1433;database=test", "dbo.table_name", connectionPropertie)
