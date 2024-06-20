package com.ez.springboot.rabbitmq.messageconsumer.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Classname DeadLetterConsumer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/6/19
 */
@Component
public class DeadLetterConsumer {

	private static final String QUEUE_NAME = "DeadLetter.queue.normal";
	private static final String EXCHANGE_NAME = "DeadLetter.exchange.normal";
	private static final String ROUTING_KEY = "normal";
	private static final String DEAD_LETTER_EXCHANGE_NAME = "DeadLetter.exchange.dead";
	private static final String DEAD_LETTER_QUEUE_NAME = "DeadLetter.queue.dead";
	private static final String DEAD_KEY_NAME = "deadKey";

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = QUEUE_NAME, durable = "true", arguments = {
					@Argument(name = "x-dead-letter-exchange", value = DEAD_LETTER_EXCHANGE_NAME),
					@Argument(name = "x-dead-letter-routing-key", value = DEAD_KEY_NAME),
					@Argument(name = "x-message-ttl", value = "10000", type = "java.lang.Integer"),
					@Argument(name = "x-max-length", value = "10", type = "java.lang.Integer")
			}),
			exchange = @Exchange(value = EXCHANGE_NAME),
			key = {ROUTING_KEY}

	))
	public void getMessage(String dataString, Message message, Channel channel) throws IOException {

		channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		// channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
	}

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = DEAD_LETTER_QUEUE_NAME),
			exchange = @Exchange(value = DEAD_LETTER_EXCHANGE_NAME),
			key = {DEAD_KEY_NAME}
	))
	public void getDeadLetterMessage(String dataString, Message message, Channel channel) throws IOException {

		System.out.println("死信接收：" + message + "，信息：" + dataString);
		long consumerTag = message.getMessageProperties().getDeliveryTag();
		// 因为在yml配置文件中设置手动接收消息，这里从死信队列中读取消息时标记签收。
		channel.basicAck(consumerTag, true);

	}
}
