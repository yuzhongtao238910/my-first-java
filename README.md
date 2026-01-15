nginx反向代理的好处

- 提高访问速度
- 保证后端服务的安全
- 进行负载均衡
  - 将大量的强求按照指定的方式均衡的进行分配给集群之中的每台服务器


```text
server {
    listen 80;
    server_name localhost;
    location /api/ {
        proxy_pass http://localhost:8080/admin/; 反向代理
    }
}
```
```text
upstream webservers {
    server 192.168.100.128:8080 weight=90 ;
    server 192.168.100.129:8080 weight=10 ;
}
server {
    listen 80;
    server_name localhost;
    location /api/ {
        proxy_pass http://webservers/admin/; 反向代理+负载均衡
    }
}
```

nginx负载均衡策略 
- 轮询 默认方式
- weight 权重方式，默认为1，权重越高，被分配的客户端请求越多
- ip_hash 依据ip分配方式，这样每个访客可以固定访问一个后端服务
- least_conn 依据最少链接方式，把请求优先分配给连接数少的后端服务
- url_hash 依据url分配方式，这样相同的url会被分配到同一个后端服务
- fair 依据响应时间方式，响应时间短的服务将会被优先分配



## 将密码加密进行存储，使用md5
123456 ---md5加密--> e10abc3949ba59abbe56e057f20f883e


DigestUtils.md5DigestAsHex

yapi.smart-xwork.cn


## swagger 
只需要按照它的规范去定义接口以及接口相关的信息，就可以做到生成接口文档，以及在线的接口调试页面
官网：https://swagger.io
Knife4j 是java mvc框架集成swagger生成api文档的增强解决方案
```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>3.0.2</version>
</dependency>
```

1- 导入maven坐标
2- 再配置类之中加入Knife4j的配置
3- 设置静态资源的映射，否则接口文档页面无法访问



## ？
通过swagger生成接口，那yapi的作用
yapi是设计阶段使用的
swagger是开发阶段使用的，帮助后端开发人员做接口测试哈


## swagger注解

@Api
    用在类上，例如Controller，表示对类的说明
@ApiModel
    用在类上，例如entity DTO VO
@ApiModelProperty
    用在属性上，描述属性信息
@ApiOperation
    用在方法上，例如Controller的方法，说明方法的用途 作用
