## 基于Socket 和 HTTP 的Web MVC自制框架
#### 1）通过底层socket 实现TCP/IP通信，多线程并发访问。

#### 2）实现了HTTP报文的接受、解析、生成、返回。

#### 3）采用MVC架构，控制层、显示层、数据层代码分离，代码便于复用，可拓展性强。
  * M层：模型层使用自制的基于 MySQL 的类 Mybatis 的数据持久层，提供 POJO 和 SQL之间的类型映射和绑定。
  * V层：模板为freemarker，封装单例渲染生成模板。
  * C层：控制器层使用 HashMap 和 Method Reference 实现路由分发、自定义请求和自定义响应封装。
#### 本项目为该框架的试用项目。
* 实现了基本的用户注册，登录。
* 个人主页，留言板，TODO的增删改查功能。
* 使用服务端Session实现状态保持。
* 密码加盐Hash存储。

## 运行环境
* Mac10.14  
* IDEA
* JDK12
```
运行Main.java即可
```

## 本地测试，测试前请自行修改 application.properties 文件配置本地 SQL

## 详情
#### 主页
![image](https://github.com/etlRlks/etlrlksMVC/blob/master/img/%E4%B8%BB%E9%A1%B5.png)
#### 注册示例
![image](https://github.com/etlRlks/etlrlksMVC/blob/master/img/%E6%B3%A8%E5%86%8C.png)
#### 登录页
![image](https://github.com/etlRlks/etlrlksMVC/blob/master/img/%E7%99%BB%E5%BD%95%E9%A1%B5.png)
#### 登录示例
![image](https://github.com/etlRlks/etlrlksMVC/blob/master/img/%E7%99%BB%E5%BD%95%E6%88%90%E5%8A%9F.png)
#### TODO
![image](https://github.com/etlRlks/etlrlksMVC/blob/master/img/TODO.png)
#### TODO增删改查
![image](https://github.com/etlRlks/etlrlksMVC/blob/master/img/TODO%E5%A2%9E%E5%88%A0%E6%94%B9%E6%9F%A5.gif)


