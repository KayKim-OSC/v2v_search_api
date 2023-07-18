#!/bin/sh
SERVICE_NAME=v2v_search_api
PATH_TO_JAR=./lib/v2v_search_api-1.0.0.jar
PID_PATH_NAME=./$SERVICE_NAME.pid
JAVA_OPT="-Xms1024m -Xmx2048m -Dspring.config.location=config/application-prod.yml"

case $1 in
    start)
        if [ -f $PID_PATH_NAME ]; then
             PID=$(cat $PID_PATH_NAME);
             echo "$SERVICE_NAME already stated...please check the service PID = $PID"
        else
            echo "$SERVICE_NAME starting ..."
            nohup java -jar $JAVA_OPT $PATH_TO_JAR >> /dev/null &
            echo $! > $PID_PATH_NAME
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME started ...PID = $PID"
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "please wait $SERVICE_NAME stopping ...PID = $PID"
            kill $PID;
	    sleep 5
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "please wait $SERVICE_NAME stopping ...kill PID = $PID";
            kill $PID;
	    sleep 5
            echo "$SERVICE_NAME stopped ...";
            rm $PID_PATH_NAME
            echo "$SERVICE_NAME starting ..."
            nohup java -jar $JAVA_OPT $PATH_TO_JAR >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
esac
