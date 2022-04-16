PIDS=`ps -ef|grep java |grep -v grep| awk '{print $2}'`
for pid in $PIDS
do 
	kill -15 $pid
done
