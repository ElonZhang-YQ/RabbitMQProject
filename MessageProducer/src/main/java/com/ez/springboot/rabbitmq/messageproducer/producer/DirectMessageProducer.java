package com.ez.springboot.rabbitmq.messageproducer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname DIrectMessageProducer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@Component
public class DirectMessageProducer {

    private static final String DIRECT_MESSAGE_QUEUE = "DirectMessageQueue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 只绑定Queue，发送消息
     * @param message
     */
    public void sendDirectMessage(String message) {
        rabbitTemplate.convertAndSend(DIRECT_MESSAGE_QUEUE, message);
    }
}
