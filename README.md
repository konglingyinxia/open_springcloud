#### 介绍
 1.该项目为springcloud 分布式微服务 模板架构项目，项目中集成各种组件
    极大的方便了 企业和个人快速开发，快速交付。
 
 2.项目地址：
   
### 软件架构
 1、使用技术：
    
    spring boot :2.1.4.RELEASE
    springcloud :Greenwich.SR1
    mybatis-plus:3.0.7.1
    redis:5.0.5  开发 （ 3.0.503）
    mysql : 5.5
    jdk: 1.8
    txlcn: 5.0.2.RELEASE (分布式事务解决组件)
    zipkin: 2.1.1.RELEASE (链路追踪 ，服务监控)
    eureka：2.1.1.RELEASE (注册中心)
    
 2、项目目录结构：
 
    common-eureka-server:注册中心服务
    
    common-fegin-server：各服务fegin 调用业务接口api 
    
    common-gateway-server: 网关服务
    
    common-txlcn-manager-server: 引入txlcn 事务管理器 服务
    
    common-zipkin-server: 链路追踪服务
    
    common-item-config：服务模块公共配置
    
    common-item-constant：服务模块常量
    
    common-item-util：服务模块集成工具包
    db： 数据库文件：
    
          1.common-txlcn-magager-server.sql 事务管理器服务数据库
          2.project-bourse-data-kline.sql 金融数据服务数据库
          3.project-bourse-data-kline 生成八字 服务 数据库
            
    img:说明文档引入图片
    
    doc: 项目文档等资料存放目录
    
    log:日志目录
    
    page：各服务管理后台页面
    
    -----------------------各独立服务项目模块--------------------------------------------------
    
    project-bourse-data-kline：金融数据服务
    
    project-open-birthdate-item：生成八字 服务
    
    project-platform-item: 权限管理快速平台 服务
    
  3、项目配置文件说明：
  
  
 4、项目登陆权限校验 (基于拦截器)
    
    使用技术：redis+JWT 
    
    具体方法： 
        1。用户登陆后生成 jwt token 信息，每次 生成 token 信息都不一样
        2。存储到redis 中，设置超时
        3。用户登陆成功后，返回前端token 信息
        4。前端请求接口 时 把 token 放入 heder 头中
        5。每次请求，通过拦截器校验 token 信息
        6。登陆状态
           未登录：拦截器中获取不到 token  信息 则用户未登陆
           单设备登录：请求头中获取的 token信息 与 redis 中存储的不一致 则用户已在其他地方登陆
           登陆超时：redis 中 获取不到用户的 token 信息，则用户登陆超时
            

    
 5、项目启动顺序
 
 ```
    common-eureka-server --> common-txlcn-manager-server
                                        |
                                        V 
    common-gateway-server <--  common-zipkin-server
            |
            V
    project-platform-item  .... 等其他各服务模块
```

 

#### 安装教程
 
    1、搭建服务器环境 
        1：到 `https://oneinstack.com/` 网站自定义安装包 （数据库，redis ,jdk ,nginx）
        2:服务器上执行从上面网站复制的 命令：
        
   `wget -c http://mirrors.linuxeye.com/oneinstack-full.tar.gz && tar xzf oneinstack-full.tar.gz && ./oneinstack/install.sh --nginx_option 1 --jdk_option 2 --db_option 4 --dbinstallmethod 1 --dbrootpwd oneinstack --redis  --reboot`
        
    2、创建数据库，
    3、服务器上创建项目目录 文件：
        1：静态资源目录：/home/project/staticFile
        2：配置文件目录：/home/project/config
        3: 日志目录：/home/project/log
        4：启动脚本：
            把doc 文件下的shell 脚本 放入 /home/project/ 目录下
            修改脚本名字为：charge.sh
            修改启动脚本为可执行：chmod 777  charge.sh
            修改脚本文件里启动项目名字： 为 change-1.0.jar
     
   ![Image text](./img/1562034264(1).jpg)
   
    4、项目打包成 jar 包 修改名字 为charge-1.0.jar  上传到服务器 /home/project/ 目录下 
         启动项目 ： ./charge.sh  restart 
         查看实时日志： tail -f ./log/catalina.out 
           
    5、上传静态资源 前端文件 到服务器  /home/project/staticFile/ 目录下
        1、该目录下如果创建后台管理页面目录  admin(或其他名字)  则把静态页面放到 admin 目录下
            访问路径为：http://ip:端口/admin/index.html
        2、如果index.html 在  /home/project/staticFile/ 目录下
            则项目访问路径为：http://ip:端口/index.html
        3、可在 /home/project/staticFile/ 下创建多个项目的静态资源文件目录，
            访问路径为：http://ip:端口/静态资源文件目录/index.html
    6、使用 nginx 转发项目 ，在nginx 里配置 https  访问

#### 使用说明


#### 网站：

    


#### 参与贡献


#### 备注：

