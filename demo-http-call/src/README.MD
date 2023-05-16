### HttpClient

httpClient的get或post请求方式步骤：

1.生成一个HttpClient对象并设置相应的参数；
2.生成一个GetMethod对象或PostMethod并设置响应的参数；
3.用HttpClient生成的对象来执行GetMethod生成的Get方法；
4.处理响应状态码；
5.若响应正常，处理HTTP响应内容；
6.释放连接。

pom文件中需要引入：

```xml
<!--HttpClient-->
<dependency>
    <groupId>commons-httpclient</groupId>
    <artifactId>commons-httpclient</artifactId>
    <version>3.1</version>
</dependency>

<!--fastjson-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.32</version>
</dependency>
```

