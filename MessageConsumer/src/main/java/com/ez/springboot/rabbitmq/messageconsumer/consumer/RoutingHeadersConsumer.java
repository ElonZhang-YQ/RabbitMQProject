package com.ez.springboot.rabbitmq.messageconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @Classname RoutingHeadersConsumer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@Component
public class RoutingHeadersConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "RoutingHeadersMessageQueue"),
                    exchange = @Exchange(value = "RoutingHeaderExchange", type = "headers")
            )
    })
    public void getRoutingHeadersMessage(String message, @Header("RoutingHeadersKey") String header) {

        System.out.println("Routing, Headers : " + header + ", Message : " + message);
    }
}
