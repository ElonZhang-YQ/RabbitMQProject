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

- 在后续更新中，会添加持久化，死信等功能代码
