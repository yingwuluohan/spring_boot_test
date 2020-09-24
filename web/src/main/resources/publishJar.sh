#!/bin/bash
if [ $# -lt 2 ]
then
  echo "you must use like this : ./publishJar.sh <projectname> <port> <active>"
  exit 1
fi
SERVER_NAME="$1"
PORT=$2
ACTIVE=$3
BASE_PATH="/data/servers/${SERVER_NAME}"

OPTS="--spring.application.name=${SERVER_NAME} --server.port=${PORT}"
if [ -n "$ACTIVE" ]
then
  OPTS="$OPTS --spring.profiles.active=${ACTIVE}"
fi

BAK_DIR="/data/publish/backup_data/$SERVER_NAME"
if [ ! -d $BAK_DIR ]
then
  echo "create bak dir: $BAK_DIR"
  mkdir -p $BAK_DIR
  mkdir -p "/data/logs/${SERVER_NAME}"
fi

HAS_OLD_JAR=`ls $BASE_PATH | grep -c ${SERVER_NAME}-[0-9].**.jar`
if [ $HAS_OLD_JAR -gt 0 ]
then
  echo "backup old jar $HAS_OLD_JAR"
  mv -f ${BASE_PATH}/${SERVER_NAME}-[0-9].**.jar $BAK_DIR/${SERVER_NAME}_`date +%H%M%S`.jar
fi

if [ ! -d $BASE_PATH ]
then
  echo "create server dir: $BASE_PATH"
  mkdir -p $BASE_PATH
fi

mv -f /data/publish/data/${SERVER_NAME}-**.jar $BASE_PATH
JAR_FILE=`find ${BASE_PATH}/${SERVER_NAME}-**.jar`
if [ -f "$JAR_FILE" ]
then
  chmod 777 $JAR_FILE
  echo deploying $JAR_FILE
  ps -ef | grep $SERVER_NAME-[0-9].**.jar | grep -v grep | awk '{print $2}' | xargs kill -9
  echo "start server $JAR_FILE"
  nohup java -javaagent:/data/apm/elastic-apm-agent-1.17.0.jar -Delastic.apm.enable_log_correlation=true -Delastic.apm.environment=$ACTIVE -Delastic.apm.service_name=$SERVER_NAME -Delastic.apm.server_url=http://uwork-apm.gokuaidian.com -Delastic.apm.application_packages=com.fp.*,tech.uwork.*,com.xxl.job.*,com.gkd.*,com.czb.*,com.gokuaidian.* -jar -Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom $JAR_FILE $OPTS > /data/logs/${SERVER_NAME}/console.log 2>&1&
  echo "$JAR_FILE published"
else
  echo "error : $JAR_FILE not exists"
  exit 1
fi