#!/bin/bash
# 进入部署目录
cd `dirname $0`
cd ..
####################
# 常量
####################
# 项目名称
PROJECT_NAME=study-netty
# 部署目录
DEPLOY_HOME=`pwd`
# conf目录
CONF_DIR=$DEPLOY_HOME/conf
# lib目录
LIB_DIR=$DEPLOY_HOME/lib
# 日志目录
LOG_DIR=/app/logs/$PROJECT_NAME

# 标准输出文件
STDOUT_FILE=$LOG_DIR/stdout.%Y-%m-%d.log
# GC日志文件
GC_LOG_FILE=$LOG_DIR/gc.log

# jar路径组装成classpath格式
LIB_JARS=`ls $LIB_DIR |grep .jar |awk '{print "'$LIB_DIR'/"$0}' |tr "\n" ":"`
####################
# java参数
####################
JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
#JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=13002 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
JAVA_MEM_OPTS=" -server -Xms5g -Xmx5g -Xmn768m -Xss256k -XX:PermSize=128m -XX:MaxPermSize=512m -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
JAVA_GC_OPTS=" -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintClassHistogram -XX:-TraceClassUnloading -verbose:gc -Xloggc:"$GC_LOG_FILE

#alias gpid="ps -ef |grep $CONF_DIR |grep $LIB_DIR |grep -v grep |awk '{print $2}'"
#pid=`gpid`
#alias psp="ps -ef |grep $CONF_DIR |grep $LIB_DIR |grep -v grep"
#shopt -s  expand_aliases
#shopt expand_aliases

####################
# 环境变量
####################
#source /etc/profile
export JAVA_HOME=/usr/jdk1.8.0_162
export PATH=$PATH:$JAVA_HOME/bin
####################
# 获取PID
####################
get_pid(){
    pid=`ps -ef |grep $CONF_DIR |grep $LIB_DIR |grep -v grep |awk '{print $2}'`
    echo "$pid"
}
####################
# 启动服务
####################
JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -Xmn256m -Xss256k -XX:PermSize=128m -XX:MaxPermSize=512m"
JAVA_GC_OPTS=" -XX:+PrintGCDetails -XX:+PrintGCTimeStamps "
JAVA_OPTS=$JAVA_MEM_OPTS $JAVA_GC_OPTS
start() {
    echo "Starting server ..."
    pid=`get_pid`
    if [ -n "$pid" ]; then
        echo "ERROR: Server running on $pid"
        exit 1
    fi
    nohup java $JAVA_OPTS -cp $CONF_DIR:$LIB_JARS com.company.project.Startup 2>&1 &
    if [ $? -eq 0 ]; then
        pid=`get_pid`
        echo "STARTED PID: $pid"
        return 0
    else
        echo "ERROR: Start failure![code: $?]"
        exit 1
    fi
}
####################
# 停止服务
####################
stop() {
    echo "Stopping server ... "
    pid=`get_pid`
    if [ -z "$pid" ]; then
        echo "ERROR: No server to stop!"
        exit 1
    fi
    kill -9 $pid
    if [ $? -eq 0 ]; then
        echo "STOPPED PID: $pid"
        return 0
    else
        echo "ERROR: Stop failure![code: $?]"
        exit 1
    fi
}
####################
# 查看服务状态
####################
status() {
    pid=`get_pid`
    if [ -z "$pid" ]; then
        echo "Not running"
    else
        echo "Running PID: $pid"
    fi
    exit 0
}
####################
# 入口
####################
case $1 in
    start)
        start
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    restart)
        pid=`get_pid`
        if [ -n "$pid" ]; then
            stop
        fi
        start
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}"
        exit 1
        ;;
esac
