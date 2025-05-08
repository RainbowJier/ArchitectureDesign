package com.example.system.component;

import com.example.common.enums.BizCode;
import com.example.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;


/**
 * 发送邮箱验证码组件
 */

@Slf4j
@Component
public class EmailComponent {

    @Value("${spring.mail.username}")
    private String sendMailer;

    /**
     * 注入邮件工具类
     */
    @Resource
    private JavaMailSenderImpl javaMailSender;

    /**
     * 异步发送邮箱验证码
     */
    @Async("threadPoolTaskExecutor")
    public void send(String receive,String value) {
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);

            mimeMessageHelper.setFrom(sendMailer);
            mimeMessageHelper.setTo(receive);
            mimeMessageHelper.setSubject("Register Verification Code");
            mimeMessageHelper.setText(value);
            mimeMessageHelper.setSentDate(new Date());

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            System.out.println("发送邮件成功：" + sendMailer + "===>" + receive);
        } catch (MessagingException e) {
            throw new BizException(BizCode.FAIL_SEND_EMAIL);
        }
    }
}
