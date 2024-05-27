package com.ez.springboot.rabbitmq.messageconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname DirectMessageConsumer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@Component
public class DirectMessageConsumer {

    /**
     * 简单的通过队列接收消息
     * 如果没有队列”DirectMessageQueue“，就会先创建这个队列，然后接收消息
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(value="DirectMessageQueue"))
    public void getDirectMessage(String message) {
        System.out.println(message);
    }
}
