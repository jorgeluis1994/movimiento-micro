package com.dev.bank.movimientos.config;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange movimientosExchange() {
        return new TopicExchange("movimientosExchange");
    }

}