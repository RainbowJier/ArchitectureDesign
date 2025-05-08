package com.example.system.service;

import com.example.common.enums.SendCode;
import com.example.common.util.JsonData;

import javax.mail.MessagingException;

public interface NotifyService {
    JsonData sendCode(String keyType, String to) throws MessagingException;


    boolean checkCode(String keyType, String to, String code);


}
