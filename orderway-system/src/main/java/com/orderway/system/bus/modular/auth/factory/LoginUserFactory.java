/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.auth.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.mysql.cj.util.StringUtils;
import com.orderway.core.consts.CommonConstant;
import com.orderway.core.context.constant.ConstantContextHolder;
import com.orderway.core.enums.CommonStatusEnum;
import com.orderway.core.exception.ServiceException;
import com.orderway.core.exception.enums.ServerExceptionEnum;
import com.orderway.core.pojo.login.LoginEmpInfo;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.tenant.consts.TenantConstants;
import com.orderway.core.tenant.context.TenantCodeHolder;
import com.orderway.core.tenant.context.TenantDbNameHolder;
import com.orderway.core.util.HttpServletUtil;
import com.orderway.core.util.IpAddressUtil;
import com.orderway.core.util.UaUtil;
import com.orderway.system.bus.core.enums.AdminTypeEnum;
import com.orderway.system.bus.core.enums.SexEnum;
import com.orderway.system.bus.modular.app.service.SysAppService;
import com.orderway.system.bus.modular.emp.service.SysEmpService;
import com.orderway.system.bus.modular.menu.service.SysMenuService;
import com.orderway.system.bus.modular.role.service.SysRoleService;
import com.orderway.system.bus.modular.user.entity.SysUser;
import com.orderway.system.bus.modular.user.service.SysUserService;
import org.apache.poi.util.StringUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录用户工厂类
 *
 * @author oderway
 * @date 2020/3/13 14:58
 */
public class LoginUserFactory {

    private static final SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);

    private static final SysEmpService sysEmpService = SpringUtil.getBean(SysEmpService.class);

    private static final SysAppService sysAppService = SpringUtil.getBean(SysAppService.class);

    private static final SysMenuService sysMenuService = SpringUtil.getBean(SysMenuService.class);

    private static final SysRoleService sysRoleService = SpringUtil.getBean(SysRoleService.class);



    /**
     * 填充登录用户相关信息
     *
     * @author oderway yubaoshan
     * @date 2020/3/13 15:01
     */
    public static void fillLoginUserInfo(SysLoginUser sysLoginUser) {
        HttpServletRequest request = HttpServletUtil.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            sysLoginUser.setLastLoginIp(IpAddressUtil.getIp(request));
            sysLoginUser.setLastLoginTime(DateTime.now().toString());
            sysLoginUser.setLastLoginAddress(IpAddressUtil.getAddress(request));
            sysLoginUser.setLastLoginBrowser(UaUtil.getBrowser(request));
            sysLoginUser.setLastLoginOs(UaUtil.getOs(request));
            Long userId = sysLoginUser.getId();

            // 员工信息
            LoginEmpInfo loginEmpInfo = sysEmpService.getLoginEmpInfo(userId);
            sysLoginUser.setLoginEmpInfo(loginEmpInfo);

            // 角色信息
            List<Dict> roles = sysRoleService.getLoginRoles(userId);
            sysLoginUser.setRoles(roles);

            // 权限信息
            List<String> permissions = sysMenuService.getLoginPermissions(userId);
            sysLoginUser.setPermissions(permissions);

            // 数据范围信息
            List<Long> dataScopes = sysUserService.getUserDataScopeIdList(userId, loginEmpInfo.getOrgId());
            sysLoginUser.setDataScopes(dataScopes);

            // 具备应用信息（多系统，默认激活一个，可根据系统切换菜单）,返回的结果中第一个为激活的系统
            List<Dict> apps = sysAppService.getLoginApps(userId);
            sysLoginUser.setApps(apps);

            // 如果根本没有应用信息，则没有菜单信息
            if (ObjectUtil.isEmpty(apps)) {
                sysLoginUser.setMenus(CollectionUtil.newArrayList());
            } else {
                //AntDesign菜单信息，根据人获取，用于登录后展示菜单树，默认获取默认激活的系统的菜单
                String defaultActiveAppCode = apps.get(0).getStr(CommonConstant.CODE);
                sysLoginUser.setMenus(sysMenuService.getLoginMenusAntDesign(userId, defaultActiveAppCode));
            }

            //如果开启了多租户功能，则设置当前登录用户的租户标识
            if (ConstantContextHolder.getTenantOpenFlag()) {
                String tenantCode = TenantCodeHolder.get();
                String dataBaseName = TenantDbNameHolder.get();
                if (StrUtil.isNotBlank(tenantCode) && StrUtil.isNotBlank(dataBaseName)) {
                    Dict tenantInfo = Dict.create();
                    tenantInfo.set(TenantConstants.TENANT_CODE, tenantCode);
                    tenantInfo.set(TenantConstants.TENANT_DB_NAME, dataBaseName);
                    sysLoginUser.setTenants(tenantInfo);
                }
                //注意，这里remove不代表所有情况，在aop remove
                TenantCodeHolder.remove();
                TenantDbNameHolder.remove();
            }

        } else {
            throw new ServiceException(ServerExceptionEnum.REQUEST_EMPTY);
        }
    }

    /**
     * 填充普通用户相关信息
     */
    public static void fillUserInfo(SysUser sysUser) {
        // 密码为空 设置密码
        if(ObjectUtil.isEmpty(sysUser.getPassword())) {
            // 获取默认的密码
            String passWord = ConstantContextHolder.getDefaultPassWord();
            // 设置密码为Md5加密后的密码
            // BCrypt.gensalt() 随机创建字符串（盐） 根据盐加密密码
            sysUser.setPassword(BCrypt.hashpw(passWord,BCrypt.gensalt()));
        }
        // 头像为空则设置头像
        if(ObjectUtil.isEmpty(sysUser.getAvatar())) {
            sysUser.setAvatar(null);
        }

        // 性别为空则设置性别
        if(ObjectUtil.isEmpty(sysUser.getSex())) {
            sysUser.setSex(SexEnum.NONE.getCode());
        }

        // 设置用户状态为正常
        sysUser.setStatus(CommonStatusEnum.ENABLE.getCode());

        // 设置用户状态为非管理员
        sysUser.setAdminType(AdminTypeEnum.NONE.getCode());
    }
}
