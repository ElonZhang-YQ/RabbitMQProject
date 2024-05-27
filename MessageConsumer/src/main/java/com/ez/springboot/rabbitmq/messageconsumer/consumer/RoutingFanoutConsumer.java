package com.ez.springboot.rabbitmq.messageconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname PublisherAndSubscribeFanoutConsumer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@Component
public class RoutingFanoutConsumer {

    /**
     * 下面两个方法，绑定相同的交换机，相同的Queue。 交换机类型为fanout。
     * fanout不依赖于routing_key的匹配，所以无需声明routing_key
     * 此时下面两个方法会根据默认配置（负载均衡）接收消息
     * @param message
     */

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "RoutingFanoutMessageQueue"),
                    exchange = @Exchange(value = "RoutingFanoutExchange", type = "fanout")
                    // fanou不依赖routing_key绑定，所以不需要声明key
            )
    })
    public void getRoutingFanout(String message) {
        System.out.println("Routing, Fanout : " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "RoutingFanoutMessageQueue"),
                    exchange = @Exchange(value = "RoutingFanoutExchange", type = "fanout")
                    // fanou不依赖routing_key绑定，所以不需要声明key
            )
    })
    public void getRoutingFanout2(String message) {
        System.out.println("Routing, Fanout Diff: " + message);
    }
}
