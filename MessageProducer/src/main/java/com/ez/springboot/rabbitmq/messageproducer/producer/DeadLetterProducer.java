package com.ez.springboot.rabbitmq.messageproducer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname DeadLetterProducer
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/6/19
 */
@Component
public class DeadLetterProducer {

	private static final String DEAD_LETTER_EXCHANGE_NAME = "DeadLetter.exchange.normal";
	private static final String DEAD_LETTER_ROUTING_KEY = "normal";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendDeadLetterMessage(String message) {

		rabbitTemplate.convertAndSend(DEAD_LETTER_EXCHANGE_NAME, DEAD_LETTER_ROUTING_KEY, message);
	}

}
