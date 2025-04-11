package com.example.frame.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎
 * @Date: 2024/11/12 9:59
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EventMessage implements Serializable {
    /**
     * 消息队列id
     */
    private String messageId;

    /**
     * 事件类型
     */
    private String eventMessageType;

    /**
     * 业务id
     */
    private String bizId;

    /**
     * 账号
     */
    private Long accountNo;

    /**
     * 消息体
     */
    private String content;

    /**
     * 异常备注
     */
    private String remark;
}