# 基于netty自定义的轻量级rpc框架

### 说明
参考自 https://blog.csdn.net/yixiao3660/article/details/51492330 改造了服务注册和发现，原项目以ip+port为单位向zookeeper注册服务，当不同机器注册了不同的服务，客户端可能会随机访问一台没有某个服务的机器。 本次改造后，类似dubbo以服务为单位暴露到zookeeper,客户端不会访问没有指定服务的机器。

### 待解决问题
- 服务下的方法未注册到zk，客户端访问服务可能会发生方法不存在的问题
- 服务注册未能走xml文件配置
- 客户端服务发现配置比较繁琐，可以尝试自定义标签的方式简化（类似<dubbo:service .../>） ... 这是轻量级rpc框架，功能当然残缺不全，所以仅供学习使用，与dubbo对比起来学习会发现dubbo的强大所在。
