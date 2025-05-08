package com.example.system;

import com.example.system.component.SmsComponent;
import com.example.system.config.SmsConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
class AccountApplicationTests {

    @Autowired
    private SmsComponent smsComponent;

    @Resource
    private SmsConfig smsConfig;

    /**
     * 测试发送邮箱
     */
    @Test
    public void contextLoads() {
        smsComponent.send("13599829312",smsConfig.getTemplateId(),"123445");
    }

}
