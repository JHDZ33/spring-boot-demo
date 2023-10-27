#!/bin/bash
#================================================================
#    Filename         run-command-with-date.sh
#    Revision         0.0.1
#    Date             2023/02/27
#    Author           JHDZ33
#================================================================
#使用示例：./run-command-with-date.sh 30 20230201,表示从20230201开始执行30次，每次执行日期加1天
#%执行次数(NUM_EXECUTIONS)
#%起始日期(START_DATE)
#================================================================
# END_OF_HEADER
#================================================================

# 检查是否传入了正确的参数
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <number of times to run> <start date in the format yyyyMMdd>" >&2
    exit 1
fi

NUM_EXECUTIONS=$1
START_DATE=$2
# 该参数是执行命令，根据具体需求进行更改
YOUR_COMMAND="/usr/bin/ossutil64 cp -r --meta Content-Type:image/jpg /photo/yyyyMMdd oss://BucketName/photo/yyyyMMdd -u"

for ((i=1; i<=$NUM_EXECUTIONS; i++))
do
	echo $YOUR_COMMAND | sed s/yyyyMMdd/$START_DATE/g
	$(echo $YOUR_COMMAND | sed s/yyyyMMdd/$START_DATE/g)
	# 增加一天
	START_DATE=$(date -d "$START_DATE +1 day" +%Y%m%d)
done
