Rinetd是Unix和Linux中TCP(重定向传输控制协议)连接的工具。
Rinetd是单一过程的服务器,可以重定向很多连接并不对机器添加额外的负担。
Rined的位置是222上的/etc/rinetd.conf
触发端121的application.yml中配置向222的7236进行转发。
rinetd.conf配置文件格式:
源地址 源端口 目的地址 目的端口
0.0.0.0 7236   192.168.1.116 8770
0.0.0.0 表示可绑定rinetd的任何可用的ip地址
启动rinetd服务: /etc/init.d/rinetd start
注意:rinetd.conf中绑定的本机端口必须没有被其它程序占用
