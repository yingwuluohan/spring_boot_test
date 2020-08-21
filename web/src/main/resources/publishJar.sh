#!/bin/bash
#use default JAVA_HOME config
#export JAVA_HOME=/usr/java/jdk1.8.0_51
#export PATH=$PATH:$JAVA_HOME/bin

#defined
PROJECT_HOME="/data/war"
TOMCAT_HOME="/data/servers/tomcat-21120-user"
TOMCAT_PORT=9001
PROJECT="$1"
stop()
{
  if [ -f $TOMCAT_HOME/bin/shutdown.sh ];
        then
          echo $"Stopping Tomcat"
          $TOMCAT_HOME/bin/shutdown.sh
          RETVAL=$?
          sleep 1
          ps -ef|grep $TOMCAT_PORT|egrep -v 'grep|init|publish|tail'|awk '{print$2}'|xargs kill -9
          echo " OK"
          # [ $RETVAL -eq 0 ] && rm -f /var/lock/...
          return $RETVAL
      fi
}

#param validate
if [ $# -lt 1 ]; then
  echo "you must use like this : ./publish.sh <projectname> [tomcat port] [tomcat home dir]"
  exit
fi
if [ "$2" != "" ]; then
   TOMCAT_PORT=$2
fi
if [ "$3" != "" ]; then
   TOMCAT_HOME="$3"
fi

#shutdown tomcat
stop
sleep 1

#bak project
sourceFile="$PROJECT_HOME"/$PROJECT.war
if [ -f $sourceFile ]; then
  echo "source file exist, start backup"
  BAK_DIR=/data/publish/backup_data/$PROJECT/`date +%Y%m%d`
  mkdir -p "$BAK_DIR"
  backupFile="$BAK_DIR"/"$PROJECT"_`date +%H%M%S`.war
  cp $sourceFile $backupFile
  echo "backup file : $backupFile"
  echo "backup file [OK]"
else
  echo "$sourceFile not exist, skip backup"
fi

#publish project
echo "$PROJECT is publishing"
rm -rf "$PROJECT_HOME"/$PROJECT/*
rm -rf "$PROJECT_HOME"/$PROJECT.war
cp /data/publish/data/$PROJECT.war "$PROJECT_HOME"/$PROJECT.war
echo "publish project [OK]"

#remove tmp
rm -rf /data/publish/data/$PROJECT.war
echo "remove temp file: /czb/publish/data/$PROJECT.war [OK]"
rm -rf $TOMCAT_HOME/work/*
echo "remove temp file: $TOMCAT_HOME/work/* [OK]"

#start tomcat
#"$TOMCAT_HOME"/bin/startup.sh
#echo "tomcat is starting [OK]"