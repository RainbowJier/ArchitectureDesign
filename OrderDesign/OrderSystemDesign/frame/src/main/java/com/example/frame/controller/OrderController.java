package com.example.frame.controller;


import com.example.frame.aop.annotation.RepeatSubmit;
import com.example.frame.aop.annotation.SysLogAnno;
import com.example.frame.constant.RedisKey;
import com.example.frame.controller.request.ProductRequest;
import com.example.frame.enums.OperationEnum;
import com.example.frame.model.JsonData;
import com.example.frame.service.OrderService;
import com.example.frame.utils.CommonUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @program: OrderDesign
 * @description: 🤓🧐🎯
 * @author: Frank
 * @create: 2025-04-11 08:32
 **/

@RestController("order")
public class OrderController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private OrderService orderService;

    /**
     * Generate order token to avoid repeating submission.
     * 生成订单令牌，避免重复提交
     */
    @GetMapping("/token")
    @SysLogAnno(description = "生成订单令牌", operateType = OperationEnum.ADD)
    public JsonData getOrderToken() {
        String accountNo = "testAccountNo";
        String token = CommonUtil.getStringNumRandom(32);

        String key = String.format(RedisKey.SUBMIT_ORDER_TOKEN_KEY, accountNo, token);
        String value = "1";

        // 保存token到redis，设置过期时间为30分钟
        stringRedisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);

        return JsonData.buildSuccess(token);
    }

    @PostMapping("/confirm")
    @RepeatSubmit
    @SysLogAnno(description = "确认订单，等待支付", operateType = OperationEnum.ADD)
    public JsonData confirmOrder(@RequestBody ProductRequest productRequest) {
        return orderService.confirmOrder(productRequest);
    }
}
