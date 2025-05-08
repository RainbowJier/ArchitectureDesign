package com.example.system.component;

import com.example.common.util.CommonUtil;
import com.example.system.config.SmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@Slf4j
@Component
public class SmsComponent {

    private static final String URL_TEMPLATE = "https://jmsms.market.alicloudapi.com/sms/send?mobile=%s&templateId=%s&value=%s";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SmsConfig smsConfig;

    /**
     * Send verification code by SMS.
     * 异步 + http 连接池
     */
    @Async("threadPoolTaskExecutor")
    public void send(String to, String templateId,String value) {
        // Millisecond timestamp
        Long begin = CommonUtil.getCurrentTimestamp();

        String url = String.format(URL_TEMPLATE, to, templateId, value);

        // Create headers entity
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + smsConfig.getAppCode());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send request and get response.
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        Long end = CommonUtil.getCurrentTimestamp();

        log.info("Send SMS, cost time:{}ms, url={},body={}", end - begin, url, response.getBody());
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Send SMS success, response message:{}", response.getBody());
        } else {
            log.error("Send SMS failed, status code:{}", response.getBody());
        }

    }
}
