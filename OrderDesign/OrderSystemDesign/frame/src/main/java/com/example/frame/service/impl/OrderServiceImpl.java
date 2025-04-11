package com.example.frame.service.impl;

import com.example.frame.config.OrderMQConfig;
import com.example.frame.controller.request.ProductRequest;
import com.example.frame.enums.BizCodeEnum;
import com.example.frame.exception.BizException;
import com.example.frame.model.JsonData;
import com.example.frame.model.entity.EventMessage;
import com.example.frame.service.OrderService;
import com.example.frame.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMQConfig orderMQConfig;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public JsonData confirmOrder(ProductRequest productRequest) {
        try {
            // 生成订单号
            String orderId = CommonUtil.getStringNumRandom(32);

            // 创建消息体
            EventMessage orderMessage = new EventMessage();
            orderMessage
                    .setBizId(orderId)    // 订单 id
                    .setContent(productRequest.getName());

            // 发送消息
            rabbitTemplate.convertAndSend(
                    orderMQConfig.getOrderCloseEventExchange(),
                    orderMQConfig.getOrderDelayBindingKey(),
                    orderMessage);

            // todo:保存到数据库

            log.info("订单创建成功，订单等待支付：1 分钟。。。");
            return JsonData.buildSuccess("订单创建成功");
        }catch (Exception e){
            throw new BizException(BizCodeEnum.PAY_ORDER_FAIL);
        }
    }

    @Override
    public void handleCloseOrder(EventMessage eventMessage) {
        String content = eventMessage.getContent();

        // 订单 id
        String orderId = eventMessage.getBizId();

        // todo：取消订单

        log.info("订单支付超时，关闭订单：{}", content);
    }

}
