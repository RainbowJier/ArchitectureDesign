package com.example.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: Check if the phone or email input format is correct or not.
 * @Author： RainbowJier
 * @Data： 2024/9/22 15:24
 */
public class CheckUtil {
    /**
     * email format: xxx@xxx.xxx
     */
    private static final Pattern MAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A￾Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * phone format
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])| (17[0-9])|(18[0-9]))\\d{8}$");


    public static boolean isEmail(String email) {
        if (null == email || email.isEmpty()) return false;

        Matcher m = MAIL_PATTERN.matcher(email);
        return m.matches();
    }


    public static boolean isPhone(String phone) {
        if (null == phone || phone.isEmpty()) return false;

        Matcher m = PHONE_PATTERN.matcher(phone);
        return m.matches();
    }

}
