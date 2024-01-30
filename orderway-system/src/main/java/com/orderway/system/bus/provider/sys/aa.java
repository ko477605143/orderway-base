package com.orderway.system.bus.provider.sys;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class aa {


    public static void main(String[] args) throws UnsupportedEncodingException {
        String a = "我是天下";
        System.out.println(URLEncoder.encode(a,"UTF-8"));
    }
}
