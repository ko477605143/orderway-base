package com.orderway.auth.bus.modular.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.orderway.auth.bus.core.cache.AppCache;
import com.orderway.auth.bus.modular.data.mapper.DataHandlerMapper;
import com.orderway.core.pojo.login.SysLoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AppHandler {

    @Resource
    private AppCache appCache;

    @Resource
    private DataHandlerMapper dataHandlerMapper;


    public boolean check_app_token(String token) {
        boolean r = false;

        Object r_object = appCache.get(token);
        if (r_object != null) {

        } else {
            r = true;
        }

        return r;
    }

    //HttpServletRequest

    public String check_app_token_by_request(HttpServletRequest request) {
        String r = "1";
        String app_token = request.getHeader("token");

        if (app_token == null || app_token.trim().equals("")) {
            r = "2";//不通过
        } else {
            //有传就是判断当前请求是app来请求
            //Object r_object = userCache.get(userCache.getCommonKeyPrefix()+app_token);
            Object r_object = appCache.get(app_token);
            if (ObjectUtil.isEmpty(r_object)) {
                r = "2";//不通过

            } else {
                r = "3";//通过

            }
        }

        return r;
    }

    public Map check_app_token_by_token(String  app_token) {
        Map map = CollectionUtil.newHashMap();

        if (app_token == null || app_token.trim().equals("")) {
            throw new HandlerException(AppHandlerExceptionEnum.TOKEN_ERR);
        } else {
            //有传就是判断当前请求是app来请求
            //Object r_object = userCache.get(userCache.getCommonKeyPrefix()+app_token);
            Object r_object = appCache.get(app_token);
            if (ObjectUtil.isEmpty(r_object)) {
                throw new HandlerException(AppHandlerExceptionEnum.TOKEN_ERR);

            } else {
                String[] s = r_object.toString().split("_");
                map = dataHandlerMapper.sel_app_by_appId(s[0]);

            }
        }

        return map;
    }

    public List<Map> get_app_name_url() {
        return dataHandlerMapper.getAppUrl();
    }


}
