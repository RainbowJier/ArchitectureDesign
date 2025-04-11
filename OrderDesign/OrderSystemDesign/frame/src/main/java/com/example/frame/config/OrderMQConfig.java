package com.example.frame.config;


import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: RabbitMQ
 * @description: 🤓🧐🎯
 * @author: Frank
 * @create: 2025-04-08 16:08
 **/

@Data
@Configuration
public class OrderMQConfig {

    /**
     * 过期时间，单位毫秒
     */
    private Integer ttl = 1000 * 10 * 1;

    // delay exchange --> delay queue --> release.queue

    /**
     * Exchange.
     */
    private String orderCloseEventExchange = "order.close.exchange";

    @Bean
    public TopicExchange orderCloseEventExchange() {
        return new TopicExchange(orderCloseEventExchange, true, false);
    }

    /**
     * release queue
     */
    private String orderReleaseQueue = "order.release.queue";
    private String orderReleaseBindingKey = "order.release.key";

    @Bean
    public Queue orderReleaseQueue() {
        return new Queue(orderReleaseQueue, true, false, false);
    }

    @Bean
    public Binding orderReleaseBinding() {
        return BindingBuilder.bind(orderReleaseQueue())
                .to(orderCloseEventExchange())
                .with(orderReleaseBindingKey);
    }

    /**
     * delay queue
     */
    private String orderDelayQueue = "order.delay.queue";
    private String orderDelayBindingKey = "order.delay.key";

    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-message-ttl", ttl);   // 设置消息过期时间
        args.put("x-dead-letter-exchange", orderCloseEventExchange);
        args.put("x-dead-letter-routing-key", orderReleaseBindingKey);

        return new Queue(orderDelayQueue, true, false, false, args);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(orderDelayQueue())
                .to(orderCloseEventExchange())
                .with(orderDelayBindingKey);
    }


}
