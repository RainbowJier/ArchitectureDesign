package com.example.frame.listener;

import com.example.frame.model.entity.EventMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎订单消费失败，异常处理队列
 * @Date: 2024/11/12 10:02
 * @Version: 1.0
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = {@Queue("traffic.error.queue")})
public class ErrorMQListener {

    @RabbitHandler
    public void orderErrorMQHandler(EventMessage eventMessage, Message message, Channel channel) throws IOException {
        log.info("【RabbitMQ异常警告】：Message:{}", message);
        log.info("【RabbitMQ异常警告】警告成功，发送通知短信");
    }
}
