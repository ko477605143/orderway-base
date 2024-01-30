package com.orderway.jwt.factory;

import cn.hutool.core.util.ObjectUtil;
import com.orderway.jwt.JwtTokenOperator;
import com.orderway.jwt.api.JwtApi;
import com.orderway.jwt.api.exception.JwtException;
import com.orderway.jwt.api.exception.enums.JwtExceptionEnum;
import com.orderway.jwt.api.pojo.config.JwtConfig;


/**
 * jwt token操作工具的生产工厂
 *
 * @author fengshuonan
 * @date 2021/1/21 18:15
 */
public class JwtTokenApiFactory {

    /**
     * 根据jwt秘钥和过期时间，获取jwt操作的工具
     *
     * @author fengshuonan
     * @date 2021/1/21 18:16
     */
    public static JwtApi createJwtApi(String jwtSecret, Integer expiredSeconds) {

        if (ObjectUtil.hasEmpty(jwtSecret, expiredSeconds)) {
            throw new JwtException(JwtExceptionEnum.JWT_PARAM_EMPTY);
        }

        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setJwtSecret(jwtSecret);
        jwtConfig.setExpiredSeconds(expiredSeconds.longValue());

        return new JwtTokenOperator(jwtConfig);
    }

}
