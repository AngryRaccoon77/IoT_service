package com.example.iotservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String QUEUE_NAME = "deviceStatusQueue1";
    private static final String QUEUE_NAME2 = "deviceStatusQueue2";

    private static final String EXCHANGE_NAME = "deviceStatusExchange";
    @Bean
    public Queue deviceStatusQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public Queue deviceStatusQueue2() {
        return new Queue(QUEUE_NAME2, false);
    }

    @Bean
    Exchange deviceStatusExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue deviceStatusQueue, Exchange deviceStatusExchange) {
        return BindingBuilder.bind(deviceStatusQueue).to(deviceStatusExchange).with("device.status").noargs();
    }

    @Bean
    Binding binding2(Queue deviceStatusQueue2, Exchange deviceStatusExchange) {
        return BindingBuilder.bind(deviceStatusQueue2).to(deviceStatusExchange).with("device.status").noargs();
    }
}