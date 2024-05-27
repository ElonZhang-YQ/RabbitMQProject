package com.ez.springboot.rabbitmq.messageconsumer.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Classname MyMessageConverterConfig
 * @Description TODO
 * @Author Elon.Zhang
 * @Date 2024/5/27
 */
@Component
public class MyMessageConverterConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
