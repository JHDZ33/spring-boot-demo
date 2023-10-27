安装步骤

    下载仓库源：wget -i -c http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm﻿
    
    安装仓库源：sudo yum install mysql57-community-release-el7-10.noarch.rpm
    
    安装mysql服务：sudo yum -y install mysql-community-server

 

授权配置  

```
启动服务

$ sudo systemctl start mysqld

日志文件

$ sudo cat /var/log/mysqld.log

配置文件

$ sudo cat /etc/my.cnf

获取临时密码

$ sudo grep password /var/log/mysqld.log

[Note] A temporary password is generated for root@localhost:
!6gtjlLe9Dc;

临时密码登录：

$ [root@iZuf69z5o9dc8y7xx758biZ log]# mysql -uroot -p

设置root密码

$ Enter password: FJV.:p1iwlK/
$ mysql> set password for root@localhost

= password('Neusense123#@!');

授权远程访问

$ mysql> grant all privileges 
on *.* to root@'%' identified by Neusense123#@!;

刷新配置

$ mysql> FLUSH PRIVILEGES;
```

验证﻿﻿
用navicae链接测试

ps:上述方式如果是centos8，会安装失败
如果是centos8安装5.7，参考文章：﻿https://blog.csdn.net/huaiyingdetective/article/details/115572943,
记得启动时设置好关闭大小写敏感
如果没有特殊要求，可以安装mysql8,它就是基于mysql5.7的改进版本，区别，可以查看文章：https://blog.csdn.net/qq_39787367/article/details/97760284﻿
mysql8安装过程：http://note.youdao.com/s/dPA0Ss5O﻿