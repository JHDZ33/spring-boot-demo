# spring-boot-demo-mq-rabbitmq

> 此 demo 主要演示了 Spring Boot 如何集成 RabbitMQ，并且演示了基于直接队列模式、分列模式、主题模式、延迟队列的消息发送和接收。

## 注意

作者编写本demo时，RabbitMQ 版本使用 `3.7.7-management`，使用 docker 运行，现修改为3.9.11版本

1. 下载镜像：`docker search rabbitmq` `docker pull rabbitmq`下载最新版

2. 运行容器：`docker run -d -p 5671:5617 -p 5672:5672 -p 4369:4369 -p 15671:15671 -p 15672:15672 -p 25672:25672 --name rabbitmq rabbitmq:latest`

3. 下载插件

地址：https://www.rabbitmq.com/community-plugins.html

找到rabbitmq_delayed_message_exchange，下载和rabbitmq对应版本的插件

4. 通过文件传输工具传到服务器上，然后复制到容器中

`docker cp /home/rabbitmq_delayed_message_exchange-3.9.0.ez rabbitmq:/plugins`

5. 进入容器：`docker exec -it rabbitmq bash`

6. 进入/plugins目录,添加插件

`rabbitmq-plugins enable rabbitmq_delayed_message_exchange`

控制台插件：

`rabbitmq-plugins enable rabbitmq_management`

使控制台可以打开channel：

`cd /etc/rabbitmq/conf.d/`

`echo management_agent.disable_metrics_collector = false > management_agent.disable_metrics_collector.conf`

查看rabbitmq版本号

rabbitmqctl version

7. 退出容器，重启该容器

`docker restart rabbitmq`

8. 进入管理页面（端口15672）

账号密码：guest
新建exchange时，type会出现`x-delayedmessage`这个选项

## 常见报错类型

###死信队列

`inequivalent arg 'x-dead-letter-exchange' for queue 'queue.direct.2' in vhost '/': received the value 'exchange.death' of type 'longstr'`
这是因为之前我们已经声明过不加死信设置的队列了，声明 queue 时试图设定一个 x-dead-letter-exchange 参数，当前服务器上该 queue 的该参数为 none，服务器不允许所以报错。

此时有两种解决方法：一是在服务器上将之前的 queue 删除，加上死信参数，再次声明队列；二是通过 policy 来设置这个参数。

## 参考

1. SpringQP 官方文档：https://docs.spring.io/spring-amqp/docs/2.1.0.RELEASE/reference/html/
2. RabbitMQ 官网：http://www.rabbitmq.com/
3. RabbitMQ延迟队列：https://www.cnblogs.com/vipstone/p/9967649.html
