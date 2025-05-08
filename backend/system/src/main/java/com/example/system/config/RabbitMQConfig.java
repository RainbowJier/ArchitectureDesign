//package com.example.system.config;
//
//import lombok.Data;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: RainbowJier
// * @Description: 👺🐉😎延迟队列配置类
// * @Date: 2024/11/12 9:40
// * @Version: 1.0
// */
//
//
//@Data
//@Configuration
//public class RabbitMQConfig {
//    /**
//     * delayer time 1 minute, unit: milliseconds
//     */
//    private Integer ttl = 1000 * 60 * 1;
//
//    /**
//     * message converter，message serializer
//     */
//    @Bean
//    public MessageConverter messageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    /**
//     * Exchange.
//     */
//    private String trafficEventExchange = "traffic.event.exchange";
//
//    @Bean
//    public TopicExchange trafficEventExchange() {
//        return new TopicExchange(trafficEventExchange, true, false);
//    }
//
//    //===============free traffic init queue==================================
//    private String trafficFreeInitRoutingKey = "traffic.free_init.routing.key";
//
//    private String trafficFreeInitQueue = "traffic.free_init.queue";
//
//    @Bean
//    public Queue trafficFreeInitQueue() {
//        return new Queue(trafficFreeInitQueue, true, false, false);
//    }
//
//    @Bean
//    public Binding trafficFreeInitBinding() {
//        return BindingBuilder.bind(trafficFreeInitQueue()).to(trafficEventExchange())
//                .with(trafficFreeInitRoutingKey);
//    }
//
//
//    //===============traffic reduction delay queue===================================
//    // delay exchange --> delay queue --> release.queue
//    private String trafficReleaseDelayRoutingKey = "traffic.release.delay.routing.key";
//    private String trafficReleaseDelayQueue = "traffic.release.delay.queue";
//
//    @Bean
//    public Queue trafficReleaseDelayQueue() {
//        Map<String, Object> args = new HashMap<>(3);
//        args.put("x-message-ttl", ttl);
//        args.put("x-dead-letter-exchange", trafficEventExchange);
//        args.put("x-dead-letter-routing-key", trafficReleaseRoutingKey);
//
//        return new Queue(trafficReleaseDelayQueue, true, false, false, args);
//    }
//
//    @Bean
//    public Binding trafficReleaseDelayBinding() {
//        return BindingBuilder.bind(trafficReleaseDelayQueue()).to(trafficEventExchange())
//                .with(trafficReleaseDelayRoutingKey);
//    }
//
//    private String trafficReleaseRoutingKey = "traffic.release.routing.key";
//    private String trafficReleaseQueue = "traffic.release.queue";
//
//    @Bean
//    public Queue trafficReleaseQueue() {
//        return new Queue(trafficReleaseQueue, true, false, false);
//    }
//
//    @Bean
//    public Binding trafficReleaseBinding() {
//        return BindingBuilder.bind(trafficReleaseQueue()).to(trafficEventExchange())
//               .with(trafficReleaseRoutingKey);
//    }
//}
