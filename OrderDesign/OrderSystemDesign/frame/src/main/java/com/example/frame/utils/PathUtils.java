package com.example.frame.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Description:对原始文件名进行修改文件名，并修改存放目录
 * @Author:Rainbow
 * @CreateTime:2023/12/2010:03
 */
public class PathUtils {
    public static String generateFilePath(String fileName) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());

        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);

        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }
}