/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.core.filter;


import com.orderway.core.context.requestno.RequestNoContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.orderway.core.consts.CommonConstant.REQUEST_NO_HEADER_NAME;

/**
 * 对请求生成唯一编码
 *
 * @author oderway
 * @date 2020/6/21 10:04
 */
public class RequestNoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 生成唯一请求号uuid
            String requestNo = UUID.randomUUID().toString();

            // 增加响应头的请求号
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.addHeader(REQUEST_NO_HEADER_NAME, requestNo);

            // 临时存储
            RequestNoContext.set(requestNo);

            // 放开请求
            chain.doFilter(request, response);

        } finally {
            // 清除临时存储的唯一编号
            RequestNoContext.clear();
        }

    }

}