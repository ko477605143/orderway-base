package com.orderway.auth.bus.core.jwt;

import com.orderway.auth.bus.core.cache.UserCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class TokenUtils {

    @Resource
    private UserCache userCache;


    public String generateToken(JwtPayLoad jwtPayLoad) {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }


}
