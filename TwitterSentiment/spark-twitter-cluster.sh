echo "Compiling Application..."

/student/cslec/robertford/sbt/bin/sbt assembly

SPARK_HOME=/student/cslec/robertford/spark-2.1.1-bin-hadoop2.7

JARFILE=`pwd`/target/scala-2.11/Twitter-assembly-1.0.jar

CONSUMERKEY=QSqAVWDJUfF2EAMlq4W3y2wHs

CONSUMERSECRET=qHp1sKmho7sshTtkkmrYY9FEUna2pmm4PCMHxcY0JbsFGRPfK5

ACCESSTOKEN=867882049394270208-jO4lL04VYKrqhM7ouDd7oyUjWiEsOyV

ACCESSTOKENSECRET=xxPEuykR4wAQWCnraIG67Dz1y4QX5Tkl6HXU225Db5oWm

${SPARK_HOME}/bin/spark-submit --class Twitter --master spark://142.150.1.123:7077 $JARFILE $CONSUMERKEY $CONSUMERSECRET $ACCESSTOKEN $ACCESSTOKENSECRET
