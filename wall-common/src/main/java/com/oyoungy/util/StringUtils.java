package com.oyoungy.util;

import java.util.Random;

public class StringUtils {
    /**
     * 剪切origin字符串的前len长的字串。
     * 原字符串长度小于len则返回原字符串
     * @param origin
     * @param len
     * @return
     */
    public static String cutStringHead(String origin, int len){
        if(origin == null){
            return "";
        }
        String newStr = origin;
        if(newStr.length() > len){
            newStr = newStr.substring(0, len);
        }
        return newStr;
    }

    /**
     * 生成随机字符串，长度最大限制为64
     * @param len
     * @return
     */
    public static String generateRandomString(int len){
        String PASSWORD_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int charLen = PASSWORD_CHARS.length();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int MAX_LEN = 64;
        len = Math.min(len, MAX_LEN);
        for(int i=0; i<len; i++){
            sb.append(PASSWORD_CHARS.charAt(random.nextInt(charLen)));
        }
        return sb.toString();
    }
}
