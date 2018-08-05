#!/bin/bash
cd `dirname $0` 		#进入命令所在的目录
if [ "$1" != "start" -a "$1" != "stop" ]; then
	echo "ERROR: Please input argument: start or stop"
	exit 1
fi

BIN_DIR=`pwd`   		#定义bin目录
cd .. 				#进入应用根目录
DEPLOY_DIR=`pwd`		#定义部署目录
CONF_DIR=$DEPLOY_DIR/conf/	#定义conf目录
LIB_DIR=$DEPLOY_DIR/lib/
				#定义lib目录
LIB_JARS=`ls $LIB_DIR | grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
				#获取lib下的所有jar包，并进行拼串

CLASSPATH=$DEPLOY_DIR:$CONF_DIR:$LIB_JARS
				#定义CLASSPATH
nohup java -classpath $CLASSPATH com.yuanheng100.settlement.startup.SettlementBootstrap $1 >/dev/null 2>&1 &
exit
