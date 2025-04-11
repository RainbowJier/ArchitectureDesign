package com.example.frame.listener;

import com.example.frame.model.entity.EventMessage;
import com.example.frame.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎C 端队列监听器（消费者）
 * @Date: 2024/11/12 10:02
 * @Version: 1.0
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = {
        @Queue("order.release.queue")
})
public class OrderMQListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void orderHandler(EventMessage eventMessage, Message message, Channel channel) {
        log.info("==========【延迟队列监听器】-Start==========");

        try {
            orderService.handleCloseOrder(eventMessage);
        } catch (Exception e) {
            log.error("【延迟队列监听器】消费异常：{}", e.getMessage());
        }
        log.info("==========【延迟队列监听器】-End==========");
    }
}
