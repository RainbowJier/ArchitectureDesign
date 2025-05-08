package com.example.common.enums;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎消息对象枚举类型
 * @Date: 2024/11/12 10:25
 * @Version: 1.0
 */

public enum EventMessageType {
    /**
     * 短链创建
     */
    SHORT_LINK_ADD,

    /**
     * C端
     */
    SHORT_LINK_ADD_LINK,

    SHORT_LINK_DEL_LINK,

    SHORT_LINK_UPDATE_LINK,

    /**
     * B端
     */
    SHORT_LINK_ADD_MAPPING,

    SHORT_LINK_DEL_MAPPING,

    SHORT_LINK_UPDATE_MAPPING,


    // ------------------ dcloud_shop ------------------
    /**
     * 新建商品订单，订单未支付
     */
    PRODUCT_ORDER_NEW,
    /**
     * 订单已经支付
     */
    PRODUCT_ORDER_PAY,

    /**
     * 免费流量包发放消息
     */
    TRAFFIC_FREE_INIT,

    /**
     * 使用流量包
     */
    TRAFFIC_USED,

}
