package com.ez.springboot.rabbitmq.messageproducer.producer;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname PublisherAndSubscribeMessageProducer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@Component
public class RoutingMessageProducer {

    /**
     * Direct 常量
     */
    private static final String EXCHANGE_NAME_DIRECT = "RoutingDirectExchange";
    private static final String ROUTING_KEY_DIRECT = "RoutingDirectKey";
    private static final String ROUTING_KEY_DIFF_DIRECT = "RoutingDirectKeyDiff";

    /**
     * Fanout 常量
     */
    private static final String EXCHANGE_NAME_FANOUT = "RoutingFanoutExchange";
    private static final String ROUTING_KEY_FANOUT = "RoutingFanoutKey";
    private static final String ROUTING_KEY_DIFF_FANOUT = "RoutingFanoutKeyDiff";

    /**
     * Topic 常量
     */
    private static final String EXCHANGE_NAME_TOPIC = "RoutingTopicExchange";
    private static final String ROUTING_KEY_TOPIC_HEAD = "Head.Routing.Topic";
    private static final String ROUTING_KEY_TOPIC_BODY = "Routing.Body.Topic";
    private static final String ROUTING_KEY_TOPIC_END= "Routing.Topic.End";

    /**
     * Headers 常量
     */
    private static final String EXCHANGE_NAME_HEADERS = "RoutingHeaderExchange";
    private static final String ROUTING_KEY_HEADERS_KEY = "RoutingHeadersKey";
    private static final String ROUTING_KEY_HEADERS_KEY_DIFF = "RoutingHeadersKeyDiff";
    private static final String ROUTING_KEY_HEADERS_VALUE = "RoutingHeadersValue";



    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送type = direct类型的消息
     * @param message
     */
    public void sendRoutingDirectMessage(String message) {

//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_DIRECT, message);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME_DIRECT, ROUTING_KEY_DIFF_DIRECT, message);
    }

    /**
     * 发送type = fanout类型的消息
     * @param message
     */
    public void sendRoutingFanoutMessage(String message) {

        rabbitTemplate.convertAndSend(EXCHANGE_NAME_FANOUT, ROUTING_KEY_FANOUT, message);
    }

    /**
     * 发送type = topic类型的消息
     * 只匹配头
     * @param message
     */
    public void sendRoutingTopicMessageHead(String message) {

        rabbitTemplate.convertAndSend(EXCHANGE_NAME_TOPIC, ROUTING_KEY_TOPIC_HEAD, message);
    }

    /**
     * 发送type = topic类型的消息
     * 只匹配中间
     * @param message
     */
    public void sendRoutingTopicMessageBody(String message) {

        rabbitTemplate.convertAndSend(EXCHANGE_NAME_TOPIC, ROUTING_KEY_TOPIC_BODY, message);
    }

    /**
     * 发送type = topic类型的消息
     * 只匹配尾
     * @param message
     */
    public void sendRoutingTopicMessageEnd(String message) {

        rabbitTemplate.convertAndSend(EXCHANGE_NAME_TOPIC, ROUTING_KEY_TOPIC_END, message);
    }

    /**
     * 发送type = headers类型的消息
     * @param message
     */
    public void sendRoutingHeadersMessage(String message) {
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader(ROUTING_KEY_HEADERS_KEY, ROUTING_KEY_HEADERS_VALUE);
                return message;
            }
        };
        // headers方式不依赖routingKey进行匹配，所以routing_key可以传空
        rabbitTemplate.convertAndSend(EXCHANGE_NAME_HEADERS, "", message, messagePostProcessor);
    }

    /**
     * 发送type = headers类型的消息
     * 设置和上面sendRoutingHeadersMessage方法不一样的head，查看是否能发送成功
     * @param message
     */
    public void sendRoutingHeadersMessageDiff(String message) {
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader(ROUTING_KEY_HEADERS_KEY_DIFF, ROUTING_KEY_HEADERS_VALUE);
                return message;
            }
        };
        // headers方式不依赖routingKey进行匹配，所以routing_key可以传空
        rabbitTemplate.convertAndSend(EXCHANGE_NAME_HEADERS, "", message, messagePostProcessor);
    }
}
