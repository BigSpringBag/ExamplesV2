#!/bin/bash

echo "Compiling application..."
/student/cslec/robertford/sbt/bin/sbt assembly

# Directory where spark-submit is defined
# Install spark from https://spark.apache.org/downloads.html
SPARK_HOME=/student/cslec/robertford/spark-2.1.1-bin-hadoop2.7

# JAR containing a simple count
JARFILE=`pwd`/target/scala-2.11/Anagram-assembly-1.0.jar

# Run it 
${SPARK_HOME}/bin/spark-submit --class Anagram --master spark://142.150.1.123:7077 $JARFILE
