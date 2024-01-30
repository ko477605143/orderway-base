package com.orderway.system.bus.modular.auth.handler;


import cn.hutool.core.util.ObjectUtil;
import com.mysql.cj.util.StringUtils;
import com.orderway.core.consts.SymbolConstant;
import com.orderway.core.exception.AuthException;
import com.orderway.core.exception.ServiceException;
import com.orderway.core.exception.enums.AuthExceptionEnum;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.pojo.node.LoginMenuTreeNode;
import com.orderway.core.util.HttpServletUtil;
import com.orderway.system.bus.modular.app.service.SysAppService;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.menu.entity.SysMenu;
import com.orderway.system.bus.modular.menu.service.SysMenuService;
import com.orderway.system.bus.modular.org.service.SysOrgService;
import com.orderway.system.bus.modular.pos.service.SysPosService;
import com.orderway.system.bus.modular.role.service.SysRoleService;
import com.orderway.system.bus.modular.user.entity.SysUser;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Component
public class HandlerNewVService {

    @Resource
    private HandlerSystemServices handlerSystemServices;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysAppService sysAppService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private SysPosService sysPosService;

    @Resource
    private AuthService authService;

    /**
     * 根据用户信息 获取对应的角色信息
     *
     * @param
     * @return List<Dict> 角色结果集
     */
    public List<Map<String, Object>> getSysLoginUserRoles() {
        SysLoginUser loginUser = this.getLoginUser();
        // 根据用户Id 从拿到当前用户的所有角色
        return sysRoleService.getAllRoleByUserId(loginUser.getId());
    }

    /**
     * 根据用户Id 获取对应的应用
     *
     * @return List<Map> 应用结果集
     */
    public List<Map> getSysLoginUserApps() {
        SysLoginUser loginUser = this.getLoginUser();
        return sysAppService.getAllAppByUserId(loginUser.getId());
    }

    /**
     * 根据token 获取当前登入用户信息
     *
     * @return
     */
    public SysLoginUser getLoginUser() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        // 从request 获取用户信息
        SysLoginUser sysLoginUser = handlerSystemServices.getSysLoginUser(request);
        return sysLoginUser;

    }

    /**
     * 根据登入用户id 对应一级菜单
     *
     * @param
     * @return 菜单结果集
     */
    public List<Map> getMenusByUserId() {
        SysLoginUser loginUser = this.getLoginUser();
        // 根据用户id 应用的编码 获取相应菜单
        return sysMenuService.getOneLevelMenusByUserId(loginUser.getId());
    }

    /**
     * 根据菜单id 获取子菜单（pid）
     */
    public List<SysMenu> getMenusByPid(Long menuId) {
        return sysMenuService.getMenusByPid(menuId);
    }

    /**
     * 获取登录用户对应的组织机构信息
     */
    public Map getLoginUserOrg() {
        SysLoginUser loginUser = this.getLoginUser();
        return sysOrgService.getUserOrg(loginUser.getId());
    }

    /**
     * 获取登录用户对应的职位信息
     */
    public List<Map> getLoginUserPos() {
        SysLoginUser loginUser = this.getLoginUser();
        return sysPosService.getUserPos(loginUser.getId());
    }

    /**
     * 根据应用code 获取应用相关信息
     *
     * @param appCode 应用编码
     * @return 应用相关信息
     */
    public Map getAppInfo(String appCode) {

        if (StringUtils.isNullOrEmpty(appCode)) {
            throw new ServiceException(HandlerExceptionEnum.PARAM_CODE_NULL);
        }

        //根据code 获取应用信息
        Map appInfo = sysAppService.getAppInfo(appCode);
        if (ObjectUtil.isEmpty(appInfo)) {
            throw new HandlerException(HandlerExceptionEnum.PARAM_CODE_ERR);
        }

        // 获取用户信息
        SysLoginUser loginUser = this.getLoginUser();

        //根据code 获取角色结果集信息
        List<Map> rolesByCode = sysRoleService.getRolesByCode(appCode, loginUser.getId());

        //根据应用code 用户id 获取菜单信息
        List<LoginMenuTreeNode> userMenu = sysMenuService.getUserMenu(loginUser.getId(), appCode);

        appInfo.put("roles", rolesByCode);
        appInfo.put("menus", userMenu);
        return appInfo;
    }

    /**
     * 根据token 返回角色，应用，用户信息
     */
    public Map getUserInfoByToken() {

        HttpServletRequest request = HttpServletUtil.getRequest();
        String token = authService.getTokenFromRequest(request);
        SysLoginUser loginUser = authService.getLoginUserByToken(token);

        //从token中获取应用编码
        String code = this.getCodeByToken(token);
        //查询编码是否存在
        Integer count = sysAppService.checkCode(code, loginUser.getId());
        if (count == 0) {
            throw new HandlerException(HandlerExceptionEnum.PARAM_CODE_ERR);
        }
        //获取用户相关信息
        return this.getAppInfo(code);

    }

    /**
     * 字符串截取
     * 根据token 截取应用信息
     */
    private String getCodeByToken(String token) {


        String code = "";
        try {
            //获取第一个下划线位置
            int front = token.indexOf(SymbolConstant.UNDER_SCORE);
            //获取最后一个下划线位置
            int last = token.lastIndexOf(SymbolConstant.UNDER_SCORE);
            //截取出code信息
            code = token.substring(front + 1, last);
        } catch (Exception e) {
            //token携带有误
            throw new AuthException(AuthExceptionEnum.NOT_VALID_TOKEN_TYPE);
        }

        return code;
    }

    /**
     * 根据token获取所有菜单
     */
    public List<Map> getAllMenuByToken(Integer type) {
        SysLoginUser loginUser = this.getLoginUser();
        return sysMenuService.getAllMenu(loginUser.getId(), type);
    }

    /**
     *
     */
    public List<Map> getAllUserByToekn(String roleCode) {
        SysLoginUser loginUser = this.getLoginUser();
        return sysRoleService.getUserByToken(loginUser.getId(), roleCode);
    }

    public List<Map> getAllRole() {
        return sysRoleService.getAllRole();
    }

}
