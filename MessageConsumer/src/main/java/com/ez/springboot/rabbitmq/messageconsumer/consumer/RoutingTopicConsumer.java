package com.ez.springboot.rabbitmq.messageconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname RoutingTopicConsumer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@Component
public class RoutingTopicConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "RoutingTopicMessageQueue"),
                    exchange = @Exchange(value = "RoutingTopicExchange", type = "topic"),
                    key = {"#.Routing.Topic"}

            )
    })
    public void getRoutingTopicMessageHead(String message) {
        /**
         * 这个方法匹配的Routing_Key，只匹配key的头部分
         */
        System.out.println("Routing, Topic Head : " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "RoutingTopicMessageQueue"),
                    exchange = @Exchange(value = "RoutingTopicExchange", type = "topic"),
                    key = {"Routing.#.Topic"}

            )
    })
    public void getRoutingTopicMessageBody(String message) {
        /**
         * 这个方法匹配的Routing_Key，只匹配key的中间部分
         */
        System.out.println("Routing, Topic Body : " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "RoutingTopicMessageQueue"),
                    exchange = @Exchange(value = "RoutingTopicExchange", type = "topic"),
                    key = {"Routing.Topic.#"}

            )
    })
    public void getRoutingTopicMessageEnd(String message) {
        /**
         * 这个方法匹配的Routing_key，只匹配key的尾部分
         */
        System.out.println("Routing, Topic End : " + message);
    }
}
