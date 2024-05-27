package com.ez.springboot.rabbitmq.messageconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname PublisherAndSubscribeConsumer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@Component
public class RoutingDirectConsumer {

    /**
     * Exchange默认配置的type就是direct，所以想要type=direct的话，默认隐式配置即可
     * direct的规则是：routing_key绑定的queue，匹配的都会投递消息。
     * 所以下面两个方法绑定相同的queue，此时生产者发送的消息这两个都会收到，不过是按照默认的负载均衡发送的消息
     * @param message
     */

    @RabbitListener(bindings = {
            @QueueBinding(
                    // 设置队列，先测试已经使用过的队列是否可行，然后再测试临时队列是否可行
//                    value= @Queue(value = "DirectMessageQueue"),    // Direct方式创建的队列不可行，尽管发送方走的是Publisher的，但是接收的还是走的Direct的，没有走Publisher的Direct
                    value= @Queue(value = "RoutingDirectMessageQueue"),   // 使用新的队列，接收的两个消息，删除服务器配置，重新运行看一下。应该是上一步测试的时候，重复绑定了。重新删除掉之后，消息接收正常。
                    // 设置路由
                    exchange = @Exchange(value = "RoutingDirectExchange"),
                    // 设置RoutingKey
                    key = {"RoutingDirectKey"}
            )
    })
    public void getRoutingDirect(String message) {
        System.out.println("Routing, Direct : " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    // 测试和上面方法同样的队列，但是key不同的情况
                    // direct类型下，只要key绑定的是相同的交换机，不同的queue，消息是不会投递的。
                    // routing_key不同，但是绑定的同一个交换机，都是会投递的。
                    value= @Queue(value = "RoutingDirectMessageQueueDiff"),
                    // 设置路由
                    exchange = @Exchange(value = "RoutingDirectExchange"),
                    // 设置RoutingKey
                    key = {"RoutingDirectKeyDiff"}    // RoutingKey设置不同
            )
    })
    public void getRoutingDirect2(String message) {
        System.out.println("Routing, Direct Diff : " + message);
    }
}
