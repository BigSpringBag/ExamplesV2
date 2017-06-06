echo "Compiling Application..."

/student/cslec/robertford/sbt/bin/sbt assembly

SPARK_HOME=/student/cslec/robertford/spark-2.1.1-bin-hadoop2.7

JARFILE=`pwd`/target/scala-2.11/HelloWorld-assembly-1.0.jar

${SPARK_HOME}/bin/spark-submit --class HelloWorld --master local $JARFILE
