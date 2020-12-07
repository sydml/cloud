package com.sydml.transaction.service;

import javax.servlet.http.Cookie;

public class Test {
    public static void main(String[] args) {
        Cookie cookie = new Cookie("testKey","测试");
//        cookie.setDomain(UrlConstants.BT_DIMAIN);
        cookie.setMaxAge(24*60*60);
    }
}
