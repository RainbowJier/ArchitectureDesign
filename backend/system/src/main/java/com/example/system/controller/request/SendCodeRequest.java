package com.example.system.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendCodeRequest {

    private String code;

    /**
     * mail / phone
     */
    private String to;


    /**
     * login/register/resetPwd
     */
    private String captchaKeyType;
}
