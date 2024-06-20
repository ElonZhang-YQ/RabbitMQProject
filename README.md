# RabbitMQ Project
- 内含两个模块，一个生产者（MessageProducer）一个消费者（MessageConsumer）
- 目前包含了一下几种MQ的发送消息的情况
  - 普通生产者消费者，只绑定了Queue。
  - Exchange, Type = direct
  - Exchange, Type = fanout
  - Exchange, Type = topic
  - Exchange, Type = headers
- 在type = direct中，比较了不同的Routing_Key绑定同一个Queue的情况和不同Queue的情况。
  - 发现不同的Routing_Key绑定相同的Queue时，这两个key都是可以接收到生产者发送的消息。
- 在type = fanout中，因为exchange不依赖于routing_key绑定，所以每一个绑定到Queue的消费者都可以接收到消息
- 在type = topic中，比较了不同Routing_Key进行模糊匹配的效果
  - header.routing.topic
  - routing.body.topic
  - routing.topic.end
- 在type = headers中，比较了生产者不同的headers，消费者是否能接收到的情况
- 死信队列(DeadLetterProducer & DeadLetterConsumer)
  - 在介绍死信队列之前，首先想说一下RabbitMQ的Ark机制（Arknowledgments）
    - RabbitMQ为了保证数据不丢失，支持消息确认机制。
    - 在数据处理之后，发送Ack，告诉RabbitMQ消息已经被接收处理，此时可以安全地从队列中删除消息了。
    - Spring-boot-start-amqp默认是自动签收消息的方式，添加配置改为手动
    ~~~yml
    server:
     port: 8081
    spring:
     application:
     name: MessageProducer
    ## 和application平级，而不是在下级或者上级
    rabbitmq:
    # IP
    host: 192.168.31.77
    # 端口
    port: 5672
    # 用户名
    username: root
    # 密码
    password: root
    # 虚拟主机
    virtual-host: /MessageProject
    # 消费端
    listener:
      simple:
        # 设置Ark机制为手动，所有消息需要手动提交Ark处理
        acknowledge-mode: manual
    ~~~
    - 所有消费端需要手动提交Ark
    ~~~java
    public void getDeadLetterMessage(String dataString, Message message, Channel channel) throws IOException {
        System.out.println("死信接收：" + message + "，信息：" + dataString);
        long consumerTag = message.getMessageProperties().getDeliveryTag();
        //因为在yml配置文件中设置手动接收消息，这里从死信队列中读取消息时标记签收。
        channel.basicAck(consumerTag, true);
    }
    ~~~

## 更新日志：
- 在后续更新中，会添加持久化，死信等功能代码
- 添加死信队列的讲解和代码 (24.06.20)

## 引用：
- [SpringBoot纯注解版的RabbitMQ死信队列](https://blog.csdn.net/weixin_45944917/article/details/108733589)
