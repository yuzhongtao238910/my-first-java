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


DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
    `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
    `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
    `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
    `sex` varchar(2) COLLATE utf8_bin NOT NULL COMMENT '性别',
    `id_number` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '身份证号',
    `status` int NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `create_user` bigint DEFAULT NULL COMMENT '创建人',
    `update_user` bigint DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='员工信息';


## 员工模块
    新增员工
        需求分析和设计
            账号、员工姓名、手机号、性别、身份证号
            employee 员工表
            注意：   账号唯一
                    手机号需要符号条件
                    性别：男或者女
                    身份证号：校验规则，长度是18位
            post请求:JSON格式的数据
                返回的数据是Result
                /admin/employee
        代码开发
            根据新增员工设计接口对应的DTO
                idNumber
                name
                phone
                sex
                username
        功能测试
        代码完善
    员工分页查询
        
    启用禁用员工账号
        
    编辑员工
        
    导入分类模块功能代码


## 区分

管理端 /admin 作为前缀
用户端 /user 作为前缀

注意：前端提交的数据和实体类对应的属性差别是比较大的时候，建议使用DTO来封装数据



✅ MyBatis 官方推荐的返回值只有两类
1️⃣ void（最常见）
void insert(Employee employee);


纯执行

自增主键通过 employee.id 回填

2️⃣ int（推荐）
int insert(Employee employee);


返回 影响行数

一般是 1

id 依然回填

int rows = employeeMapper.insert(employee);
Long id = employee.getId();



ThreadLocal并不是一个Thread，而是Thread的局部变量
ThreadLocal为每个线程提供单独一份的存储空间，具有线程的隔离效果，只有再线程内才能获取到对应的值，线程外则不能访问


客户端的每次请求都是一个单独的线程




ThreadLocal.set()
ThreadLocal.get()
ThreadLocal.remove()

员工分页的查询



方式1：在属性加注解，对日期格式化@JsonFormat()
方式2：再webMvcConfiguration之中springmvc 的消息转换器，统一对日期类型进行格式化处理

