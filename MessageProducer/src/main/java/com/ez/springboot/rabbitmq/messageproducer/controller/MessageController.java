package com.ez.springboot.rabbitmq.messageproducer.controller;

import com.ez.springboot.rabbitmq.messageproducer.producer.DirectMessageProducer;
import com.ez.springboot.rabbitmq.messageproducer.producer.RoutingMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname MessageController
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private DirectMessageProducer directMessageProducer;
    @Autowired
    private RoutingMessageProducer routingMessageProducer;

    /**
     * 只绑定queue的direct消息
     * @param message
     */
    @RequestMapping("/direct")
    public void sendDirectMessage(String message) {
        directMessageProducer.sendDirectMessage(message);
    }

    /**
     * 绑定交换机，type=direct的消息
     * @param message
     */
    @RequestMapping("/routingDirect")
    public void sendRoutingDirectMessage(String message) {
        routingMessageProducer.sendRoutingDirectMessage(message);
    }

    /**
     * 绑定交换机，type=fanout的消息
     * @param message
     */
    @RequestMapping("/routingFanout")
    public void sendRoutingFanoutMessage(String message) {
        routingMessageProducer.sendRoutingFanoutMessage(message);
    }

    /**
     * 绑定交换机，type=topic的消息，匹配头
     * @param message
     */
    @RequestMapping("/routingTopicHead")
    public void sendRoutingTopicMessageHead(String message) {
        routingMessageProducer.sendRoutingTopicMessageHead(message);
    }

    /**
     * 绑定交换机，type=topic的消息，匹配中间
     * @param message
     */
    @RequestMapping("/routingTopicBody")
    public void sendRoutingTopicMessageBody(String message) {
        routingMessageProducer.sendRoutingTopicMessageBody(message);
    }

    /**
     * 绑定交换机，type=topic的消息，匹配尾
     * @param message
     */
    @RequestMapping("/routingTopicEnd")
    public void sendRoutingTopicMessageEnd(String message) {
        routingMessageProducer.sendRoutingTopicMessageEnd(message);
    }

    /**
     * 绑定交换机，type=headers的消息
     * @param message
     */
    @RequestMapping("/routingHeaders")
    public void sendRoutingHeadersMessage(String message) {
        routingMessageProducer.sendRoutingHeadersMessage(message);
    }

    /**
     * 绑定交换机，type=headers的消息
     * 和上面方法区分，如果消费者接收的请求头是RoutingHeadersKey，那么RoutingHeadersKeyDiff能否发送成功
     * 验证：发送不成功，有报错
     * org.springframework.amqp.rabbit.support.ListenerExecutionFailedException: Listener method could not be invoked with the incoming message
     * Endpoint handler details:
     * Method [public void com.ez.springboot.rabbitmq.messageconsumer.consumer.RoutingHeadersConsumer.getRoutingHeadersMessage(java.lang.String,java.lang.String)]
     * @param message
     */
    @RequestMapping("/routingHeadersDiff")
    public void sendROutingHeadersMessageDiff(String message) {
        routingMessageProducer.sendRoutingHeadersMessageDiff(message);
    }
}
