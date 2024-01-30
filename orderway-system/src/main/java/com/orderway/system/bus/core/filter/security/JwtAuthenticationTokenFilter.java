/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.core.filter.security;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.orderway.core.context.requestno.RequestNoContext;
import com.orderway.core.exception.AuthException;
import com.orderway.core.exception.enums.ServerExceptionEnum;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.util.ResponseUtil;
import com.orderway.system.bus.modular.auth.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个过滤器，在所有请求之前，也在spring security filters之前
 * <p>
 * 这个过滤器的作用是：接口在进业务之前，添加登录上下文（SecurityContext）
 * <p>
 * 因为现在的Guns没有用session了，只能token来校验当前的登录人的身份，所以在进业务之前要给当前登录人设置登录状态
 *
 * @author oderway，xuyuxiang
 * @date 2020/4/11 13:02
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Log log = Log.get();

    @Resource
    private AuthService authService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            doFilter(request, response, filterChain);
        } catch (Exception e) {
            log.error(">>> 服务器运行异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
            ResponseUtil.responseExceptionError(response, ServerExceptionEnum.SERVER_ERROR.getCode(),
                    ServerExceptionEnum.SERVER_ERROR.getMessage(), e.getStackTrace()[0].toString());
        }
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 放开登录的接口
        String url_str = request.getRequestURI();
        log.info("===req url=>>>"+url_str);

        if(url_str.contains("/new/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1.如果当前请求带了token，判断token时效性，并获取当前登录用户信息
        SysLoginUser sysLoginUser = null;
        try {
            String token = authService.getTokenFromRequest(request);
            if (StrUtil.isNotEmpty(token)) {
                sysLoginUser = authService.getLoginUserByToken(token);
            }
        } catch (AuthException ae) {
            //token过期或者token失效的情况，响应给前端
            ResponseUtil.responseExceptionError(response, ae.getCode(), ae.getErrorMessage(), ae.getStackTrace()[0].toString());
            return;
        }

        // 2.如果当前登录用户不为空，就设置spring security上下文
        if (ObjectUtil.isNotNull(sysLoginUser)) {
            authService.setSpringSecurityContextAuthentication(sysLoginUser);
        }

        // 3.其他情况放开过滤
        filterChain.doFilter(request, response);

    }

}
