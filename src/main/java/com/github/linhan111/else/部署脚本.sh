#!/bin/bash
#author lhan111
#read -p "enter your gitlab user.name: " userName
#read -s -p "enter your gitlab user.password: " user_password
CODE_DIR="/root/ddpay/project"
JAR_NAME="manage-0.0.1-SNAPSHOT.jar"
GIT_CLONE_PATH="https://gitlab.com/privatetechteam/ddpay.git"
BRANCH_NAME="test"
APP_PROFILE="dev"


#如果任何语句的执行结果不是true则应该退出
set -e

#print helpinfo
function help() {
	echo "Usage: ./manage.sh [help|startup]"
	echo "startup:程序启动，详情参考sh脚本"
}

#考虑暴露这个方法
function shutdown() {
	PID=$(ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}')
	if [ -z "$PID" ]
		then echo Application is already stopped
	else
		echo kill $PID
		kill -9 $PID
	fi
}

function startup() {
	echo "start pull git resource code"
	rm -rf $CODE_DIR
	mkdir -p $CODE_DIR
	cd $CODE_DIR
	git clone $GIT_CLONE_PATH
	cd ddpay
	git init
	git checkout $BRANCH_NAME
	echo "git checkout succeed"
	echo "starting packaging app"
	mvn clean package -Dmaven.test.skip=true
	echo "packaging app success"
	shutdown
	cd manage/target
	nohup java -server -Xms256M -Xmx256M \
		-jar $JAR_NAME \
                -Duser.timezone=Asia/Shanghai \
		--spring.profile.active=$APP_PROFILE &
	echo "startuping success"
	echo "打开端口：8080"
  	firewall-cmd --zone=public --add-port=8080/tcp --permanent
  	firewall-cmd --reload
}

case "$1" in
	'startup') startup ;;
	'help') help ;;
 *)
esac

exit 0
