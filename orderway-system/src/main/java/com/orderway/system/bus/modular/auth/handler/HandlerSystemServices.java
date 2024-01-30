package com.orderway.system.bus.modular.auth.handler;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.orderway.core.exception.AuthException;
import com.orderway.core.exception.enums.AuthExceptionEnum;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.pojo.node.LoginMenuTreeNode;
import com.orderway.core.util.HttpServletUtil;
import com.orderway.system.bus.core.enums.AdminTypeEnum;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HandlerSystemServices {

    @Autowired
    private AuthService authService;

    @Resource
    private SysUserService sysUserService;


    /**
     * 从request中获取Token 根据Token拿到用户信息
     * @param request
     * @return
     */
    public SysLoginUser getSysLoginUser(HttpServletRequest request) {
//        Authentication authentication = authService.getAuthentication();
//        if (ObjectUtil.isEmpty(authentication) || authentication.getPrincipal() instanceof String) {
//            throw new AuthException(AuthExceptionEnum.NO_LOGIN_USER);
//        } else {
//            return (SysLoginUser) authentication.getPrincipal();
//        }
//

        String token = authService.getTokenFromRequest(request);
        return authService.getLoginUserByToken(token);
    }

    public List<Dict> getSysLoginUserApps(HttpServletRequest request) {
        String token = authService.getTokenFromRequest(request);
        return authService.getLoginUserByToken(token).getApps();
    }

    public List<Dict> getSysLoginUserRoles(HttpServletRequest request) {
        String token = authService.getTokenFromRequest(request);
        return authService.getLoginUserByToken(token).getRoles();
    }

    public List<LoginMenuTreeNode> getSysLoginUserMenus(HttpServletRequest request) {
        String token = authService.getTokenFromRequest(request);
        return authService.getLoginUserByToken(token).getMenus();
    }

    public List<Map> selMenusByUserIdAndClientType(HttpServletRequest request) {
        Map args = new HashMap();

        String token = authService.getTokenFromRequest(request);
        SysLoginUser sysLoginUser = authService.getLoginUserByToken(token);
        args.put("user_id", sysLoginUser.getId());

        if (token.contains("pc_")) {
            args.put("client_type", "0");

        } else if (token.contains("app_")) {
            args.put("client_type", "1");

        }

        return sysUserService.selMenusByUserIdAndClientType(args);
    }

    public List<Map> selAppByUserIdAndClientType(HttpServletRequest request) {
        Map args = new HashMap();

        String token = authService.getTokenFromRequest(request);
        SysLoginUser sysLoginUser = authService.getLoginUserByToken(token);
        args.put("user_id", sysLoginUser.getId());

        if (token.contains("pc_")) {
            args.put("client_type", "0");

        } else if (token.contains("app_")) {
            args.put("client_type", "1");

        }

        return sysUserService.selAppByUserIdAndClientType(args);
    }

}
