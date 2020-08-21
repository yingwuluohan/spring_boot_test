if [ $# -lt 2 ]
then
 echo "you must use like this : ./publishJar.sh <projectname> <port>"
  exit 1
fi
SERVER_NAME="$1"
PORT=$2
ACTIVE=$3
BASE_PATH="/data/servers/${SERVER_NAME}"
if [ ! -d "$BASE_PATH/backup" ]
then
  mkdir -p "$BASE_PATH/backup"
  mkdir -p "/data/logs/${SERVER_NAME}"
fi
HAS_OLD_JAR=`ls $BASE_PATH | grep -c '${SERVER_NAME}-[0-9].**.jar'`
if [ $HAS_OLD_JAR -gt 0 ]
then
  echo "backup old jar $HAS_OLD_JAR"
  mv -f ${BASE_PATH}/${SERVER_NAME}-[0-9].**.jar /data/publish/backup_data/${SERVER_NAME}
fi
mv -f /data/publish/data/${SERVER_NAME}-**.jar $BASE_PATH
echo `ls -la $BASE_PATH`
JAR_FILE=`find ${BASE_PATH}/${SERVER_NAME}-**.jar`
if [ ! -d "/data/logs/${SERVER_NAME}" ]
then
   mkdir /data/logs/${SERVER_NAME}
fi
#echo $JAR_FILE
if [ -f "$JAR_FILE" ]
then
  chmod 777 $JAR_FILE
  echo deploying $JAR_FILE
  ps -ef | grep $SERVER_NAME-[0-9].**.jar | grep -v grep | awk '{print $2}' | xargs kill -9
  echo "start server $JAR_FILE"
  nohup java -jar -Xms256m -Xmx256m -Djava.security.egd=file:/dev/./urandom $JAR_FILE --spring.profiles.active=${ACTIVE} --server.port=${PORT}> /data/logs/${SERVER_NAME}/nohup.log 2>&1&
  echo "$JAR_FILE published"
else
  echo "error : $JAR_FILE not exists"
  exit 1
fi