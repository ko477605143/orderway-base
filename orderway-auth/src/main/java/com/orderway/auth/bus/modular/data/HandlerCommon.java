package com.orderway.auth.bus.modular.data;

import cn.hutool.log.Log;
import com.orderway.core.context.constant.ConstantContextHolder;

public class HandlerCommon {


    private static final Log log = Log.get();

    public static String client_type_pc = "0";
    public static String client_type_pc_token_pre = "pc_";

    public static String client_type_app = "1";
    public static String client_type_app_token_pre = "app_";


    public static Long getPcTokenExpire() {
        return ConstantContextHolder.getSysConfigWithDefault("pc_token_expire", Long.class, 3600L);
    }


}
