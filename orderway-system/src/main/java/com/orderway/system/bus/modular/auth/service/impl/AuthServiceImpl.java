/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.orderway.core.consts.CommonConstant;
import com.orderway.core.consts.SymbolConstant;
import com.orderway.core.context.constant.ConstantContextHolder;
import com.orderway.core.dbs.CurrentDataSourceContext;
import com.orderway.core.enums.CommonStatusEnum;
import com.orderway.core.exception.AuthException;
import com.orderway.core.exception.ServiceException;
import com.orderway.core.exception.enums.AuthExceptionEnum;
import com.orderway.core.exception.enums.ServerExceptionEnum;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.core.tenant.context.TenantCodeHolder;
import com.orderway.core.tenant.context.TenantDbNameHolder;
import com.orderway.core.tenant.entity.TenantInfo;
import com.orderway.core.tenant.exception.TenantException;
import com.orderway.core.tenant.exception.enums.TenantExceptionEnum;
import com.orderway.core.tenant.service.TenantInfoService;
import com.orderway.core.util.HttpServletUtil;
import com.orderway.core.util.IpAddressUtil;
import com.orderway.system.bus.core.cache.UserCache;
import com.orderway.system.bus.core.enums.LogSuccessStatusEnum;
import com.orderway.system.bus.core.jwt.JwtPayLoad;
import com.orderway.system.bus.core.jwt.TokenUtils;
import com.orderway.system.bus.core.log.LogManager;
import com.orderway.system.bus.modular.account.entity.SysAccount;
import com.orderway.system.bus.modular.account.service.SysAccountService;
import com.orderway.system.bus.modular.app.service.SysAppService;
import com.orderway.system.bus.modular.auth.factory.LoginUserFactory;
import com.orderway.system.bus.modular.auth.factory.SysLoginUserFactory;
import com.orderway.system.bus.modular.auth.handler.HandlerCommon;
import com.orderway.system.bus.modular.auth.handler.HandlerException;
import com.orderway.system.bus.modular.auth.handler.HandlerExceptionEnum;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.emp.entity.SysEmp;
import com.orderway.system.bus.modular.emp.param.SysEmpParam;
import com.orderway.system.bus.modular.emp.service.SysEmpExtOrgPosService;
import com.orderway.system.bus.modular.emp.service.SysEmpPosService;
import com.orderway.system.bus.modular.emp.service.SysEmpService;
import com.orderway.system.bus.modular.user.entity.SysUser;
import com.orderway.system.bus.modular.user.enums.SysUserExceptionEnum;
import com.orderway.system.bus.modular.user.param.SyncUserParam;
import com.orderway.system.bus.modular.user.service.SysUserDataScopeService;
import com.orderway.system.bus.modular.user.service.SysUserRoleService;
import com.orderway.system.bus.modular.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 认证相关service实现类
 *
 * @author oderway
 * @date 2020/3/11 16:58
 */
@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private UserCache userCache;

    @Resource
    private SysEmpService sysEmpService;

    @Resource
    private SysAccountService sysAccountService;

    @Resource
    private SysEmpPosService sysEmpPosService;

    @Resource
    private SysEmpExtOrgPosService sysEmpExtOrgPosService;

    @Resource
    private SysUserDataScopeService sysUserDataScopeService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysAppService sysAppService;


    @Override
    public String login(String account, String password) {

        if (ObjectUtil.hasEmpty(account, password)) {
            LogManager.me().executeLoginLog(account, LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_EMPTY.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_EMPTY);
        }

        SysUser sysUser = sysUserService.getUserByCount(account);

        //用户不存在，账号或密码错误
        if (ObjectUtil.isEmpty(sysUser)) {
            LogManager.me().executeLoginLog(account, LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_ERROR);
        }

        String passwordBcrypt = sysUser.getPassword();

        //验证账号密码是否正确
        if (ObjectUtil.isEmpty(passwordBcrypt) || !BCrypt.checkpw(password, passwordBcrypt)) {
            LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_ERROR);
        }

        return this.doLogin(sysUser);
    }

    @Override
    public String login(String account, String password, String ctype, String appcode) {

        if (ObjectUtil.hasEmpty(account, password)) {
            LogManager.me().executeLoginLog(account, LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_EMPTY.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_EMPTY);
        }

        SysUser sysUser = sysUserService.getUserByCount(account);

        //用户不存在，账号或密码错误
        if (ObjectUtil.isEmpty(sysUser)) {
            LogManager.me().executeLoginLog(account, LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_ERROR);
        }

        String passwordBcrypt = sysUser.getPassword();

        //验证账号密码是否正确
        if (ObjectUtil.isEmpty(passwordBcrypt) || !BCrypt.checkpw(password, passwordBcrypt)) {
            LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_ERROR);
        }


        return this.doLogin(sysUser, ctype, appcode);
    }

    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        String authToken = request.getHeader(CommonConstant.AUTHORIZATION);
        if (ObjectUtil.isEmpty(authToken)) {
            return null;
        } else {
            //token不是以Bearer打头，则响应回格式不正确
            if (!authToken.startsWith(CommonConstant.TOKEN_TYPE_BEARER)) {
                throw new AuthException(AuthExceptionEnum.NOT_VALID_TOKEN_TYPE);
            }
            try {
                authToken = authToken.substring(CommonConstant.TOKEN_TYPE_BEARER.length() + 1);
            } catch (StringIndexOutOfBoundsException e) {
                throw new AuthException(AuthExceptionEnum.NOT_VALID_TOKEN_TYPE);
            }
        }

        return authToken;
    }

    @Override
    public SysLoginUser getLoginUserByToken(String token) {

        //校验token，错误则抛异常
        this.checkToken(token);

        //根据token获取JwtPayLoad部分
        //JwtPayLoad jwtPayLoad = JwtTokenUtil.getJwtPayLoad(token);
        JwtPayLoad jwtPayLoad = new JwtPayLoad();
        jwtPayLoad.setUuid(token);

        //从redis缓存中获取登录用户
        Object cacheObject = userCache.get(jwtPayLoad.getUuid());

        //用户不存在则表示登录已过期
        if (ObjectUtil.isEmpty(cacheObject)) {
            throw new AuthException(AuthExceptionEnum.LOGIN_EXPIRED);
        }

        //转换成登录用户
        SysLoginUser sysLoginUser = (SysLoginUser) cacheObject;

        //用户存在, 无痛刷新缓存，在登录过期前活动的用户自动刷新缓存时间
        this.cacheLoginUser(jwtPayLoad, sysLoginUser);

        //返回用户
        return sysLoginUser;
    }

    @Override
    public void logout() {

        HttpServletRequest request = HttpServletUtil.getRequest();

        if (ObjectUtil.isNotNull(request)) {

            //获取token
            String token = this.getTokenFromRequest(request);

            //如果token为空直接返回
            if (ObjectUtil.isEmpty(token)) {
                return;
            }

            //校验token，错误则抛异常，待确定
//            this.checkToken(token);

            //从redis缓存中获取登录用户 用户为null直接返回
            Object cacheObject = userCache.get(token);
            if (ObjectUtil.isEmpty(cacheObject)) {
                return;
            }

//            //根据token获取JwtPayLoad部分
//            JwtPayLoad jwtPayLoad = JwtTokenUtil.getJwtPayLoad(token);
//
//            //获取缓存的key
//            String loginUserCacheKey = jwtPayLoad.getUuid();
            //this.clearUser(loginUserCacheKey, jwtPayLoad.getAccount());

            //转换成登录用户
            SysLoginUser sysLoginUser = (SysLoginUser) cacheObject;
            this.clearUser(token, sysLoginUser.getAccount());

        } else {
            throw new ServiceException(ServerExceptionEnum.REQUEST_EMPTY);
        }
    }

    @Override
    public void setSpringSecurityContextAuthentication(SysLoginUser sysLoginUser) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        sysLoginUser,
                        null,
                        sysLoginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public void checkToken(String token) {
        //校验token是否正确
        // Boolean tokenCorrect = JwtTokenUtil.checkToken(token);

        //从redis缓存中获取登录用户
        Object cacheObject = userCache.get(token);

        //用户不存在则表示登录已过期
        if (ObjectUtil.isEmpty(cacheObject)) {
            throw new AuthException(AuthExceptionEnum.REQUEST_TOKEN_ERROR);
        }


//        if (!tokenCorrect) {
//            throw new AuthException(AuthExceptionEnum.REQUEST_TOKEN_ERROR);
//        }
//
//        //校验token是否失效
//        Boolean tokenExpired = JwtTokenUtil.isTokenExpired(token);
//        if (tokenExpired) {
//            throw new AuthException(AuthExceptionEnum.LOGIN_EXPIRED);
//        }
    }

    @Override
    public void cacheTenantInfo(String tenantCode) {
        if (StrUtil.isBlank(tenantCode)) {
            return;
        }

        // 从spring容器中获取service，如果没开多租户功能，没引入相关包，这里会报错
        TenantInfoService tenantInfoService = null;
        try {
            tenantInfoService = SpringUtil.getBean(TenantInfoService.class);
        } catch (Exception e) {
            throw new TenantException(TenantExceptionEnum.TENANT_MODULE_NOT_ENABLE_ERROR);
        }

        // 获取租户信息
        TenantInfo tenantInfo = tenantInfoService.getByCode(tenantCode);
        if (tenantInfo != null) {
            String dbName = tenantInfo.getDbName();

            // 租户编码的临时存放
            TenantCodeHolder.put(tenantCode);

            // 租户的数据库名称临时缓存
            TenantDbNameHolder.put(dbName);

            // 数据源信息临时缓存
            CurrentDataSourceContext.setDataSourceType(dbName);
        } else {
            throw new TenantException(TenantExceptionEnum.CNAT_FIND_TENANT_ERROR);
        }
    }

    @Override
    public SysLoginUser loadUserByUsername(String account) throws UsernameNotFoundException {
        SysLoginUser sysLoginUser = new SysLoginUser();
        SysUser user = sysUserService.getUserByCount(account);
        BeanUtil.copyProperties(user, sysLoginUser);
        return sysLoginUser;
    }

    /**
     * 根据key清空登陆信息
     *
     * @author oderway
     * @date 2020/6/19 12:28
     */
    private void clearUser(String loginUserKey, String account) {
        //获取缓存的用户
        Object cacheObject = userCache.get(loginUserKey);

        //如果缓存的用户存在，清除会话，否则表示该会话信息已失效，不执行任何操作
        if (ObjectUtil.isNotEmpty(cacheObject)) {
            //清除登录会话
            userCache.remove(loginUserKey);
            //创建退出登录日志
            LogManager.me().executeExitLog(account);
        }
    }

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 执行登录方法
     *
     * @author oderway
     * @date 2020/3/12 10:43
     */
    private String doLogin(SysUser sysUser) {

        Integer sysUserStatus = sysUser.getStatus();

        //验证账号是否被冻结
        if (CommonStatusEnum.DISABLE.getCode().equals(sysUserStatus)) {
            LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_FREEZE_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_FREEZE_ERROR);
        }

        //构造SysLoginUser
        SysLoginUser sysLoginUser = this.genSysLoginUser(sysUser);

        //构造jwtPayLoad
        JwtPayLoad jwtPayLoad = new JwtPayLoad(sysUser.getId(), sysUser.getAccount());

        //生成token
        //String token = JwtTokenUtil.generateToken(jwtPayLoad);
        String token = tokenUtils.generateToken(jwtPayLoad);
        jwtPayLoad.setUuid(token);

        //缓存token与登录用户信息对应, 默认2个小时
        this.cacheLoginUser(jwtPayLoad, sysLoginUser);

        //设置最后登录ip和时间
        sysUser.setLastLoginIp(IpAddressUtil.getIp(HttpServletUtil.getRequest()));
        sysUser.setLastLoginTime(DateTime.now());

        //更新用户登录信息
        sysUserService.updateById(sysUser);

        //登录成功，记录登录日志
        LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.SUCCESS.getCode(), null);

        //登录成功，设置SpringSecurityContext上下文，方便获取用户
        this.setSpringSecurityContextAuthentication(sysLoginUser);

        //如果开启限制单用户登陆，则踢掉原来的用户
        Boolean enableSingleLogin = ConstantContextHolder.getEnableSingleLogin();
        if (enableSingleLogin) {

            //获取所有的登陆用户
            Map<String, SysLoginUser> allLoginUsers = userCache.getAllKeyValues();
            for (Map.Entry<String, SysLoginUser> loginedUserEntry : allLoginUsers.entrySet()) {

                String loginedUserKey = loginedUserEntry.getKey();
                SysLoginUser loginedUser = loginedUserEntry.getValue();

                //如果账号名称相同，并且redis缓存key和刚刚生成的用户的uuid不一样，则清除以前登录的
                if (loginedUser.getName().equals(sysUser.getName())
                        && !loginedUserKey.equals(jwtPayLoad.getUuid())) {
                    this.clearUser(loginedUserKey, loginedUser.getAccount());
                }
            }
        }

        //返回token
        return token;
    }

    private String doLogin(SysUser sysUser, String cType, String appcode) {

        Integer sysUserStatus = sysUser.getStatus();

        //验证账号是否被冻结
        if (CommonStatusEnum.DISABLE.getCode().equals(sysUserStatus)) {
            LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_FREEZE_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_FREEZE_ERROR);
        }

        //构造SysLoginUser
        SysLoginUser sysLoginUser = this.genSysLoginUser(sysUser);

        //构造jwtPayLoad
        JwtPayLoad jwtPayLoad = new JwtPayLoad(sysUser.getId(), sysUser.getAccount());

        //生成token
        //String token = JwtTokenUtil.generateToken(jwtPayLoad);
        String token = tokenUtils.generateToken(jwtPayLoad);

        if (ObjectUtil.isNotEmpty(appcode)) {
            //判断是否有这个code/权限
            Integer count = sysAppService.checkCode(appcode,sysUser.getId());
            if(count == 0) {
                throw new HandlerException(HandlerExceptionEnum.PARAM_CODE_ERR);
            }

            if (cType.equals("0")) {///pc端
                token = HandlerCommon.client_type_pc_token_pre + appcode + SymbolConstant.UNDER_SCORE + token;
            } else if (cType.equals("1")) {///app端
                token = HandlerCommon.client_type_app_token_pre + appcode + SymbolConstant.UNDER_SCORE + token;
            } else {
                token = HandlerCommon.client_type_pc_token_pre + appcode + SymbolConstant.UNDER_SCORE + token;
            }

        } else {
            if (cType.equals("0")) {///pc端
                token = HandlerCommon.client_type_pc_token_pre + token;
            } else if (cType.equals("1")) {///app端
                token = HandlerCommon.client_type_app_token_pre + token;
            } else {
                token = HandlerCommon.client_type_pc_token_pre + token;
            }

        }

        jwtPayLoad.setUuid(token);

        //
        sysLoginUser.setClientType(cType);

        //缓存token与登录用户信息对应, 默认2个小时
        this.cacheLoginUser(jwtPayLoad, sysLoginUser);

        //设置最后登录ip和时间
        sysUser.setLastLoginIp(IpAddressUtil.getIp(HttpServletUtil.getRequest()));
        sysUser.setLastLoginTime(DateTime.now());

        //更新用户登录信息
        sysUserService.updateById(sysUser);

        //登录成功，记录登录日志
        LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.SUCCESS.getCode(), null);

        //登录成功，设置SpringSecurityContext上下文，方便获取用户
        this.setSpringSecurityContextAuthentication(sysLoginUser);

        //如果开启限制单用户登陆，则踢掉原来的用户
        Boolean enableSingleLogin = ConstantContextHolder.getEnableSingleLogin();
        if (enableSingleLogin) {

            //获取所有的登陆用户
            Map<String, SysLoginUser> allLoginUsers = userCache.getAllKeyValues();
            for (Map.Entry<String, SysLoginUser> loginedUserEntry : allLoginUsers.entrySet()) {

                String loginedUserKey = loginedUserEntry.getKey();
                SysLoginUser loginedUser = loginedUserEntry.getValue();

                //如果账号名称相同，并且redis缓存key和刚刚生成的用户的uuid不一样，则清除以前登录的
                if (loginedUser.getName().equals(sysUser.getName())
                        && !loginedUserKey.equals(jwtPayLoad.getUuid())) {
                    this.clearUser(loginedUserKey, loginedUser.getAccount());
                }
            }
        }

        //返回token
        return token;
    }

    /**
     * 构造登录用户信息
     *
     * @author oderway
     * @date 2020/3/12 17:32
     */
    @Override
    public SysLoginUser genSysLoginUser(SysUser sysUser) {
        SysLoginUser sysLoginUser = new SysLoginUser();
        BeanUtil.copyProperties(sysUser, sysLoginUser);
        // 原先走的的这条
        //LoginUserFactory.fillLoginUserInfo(sysLoginUser);
        SysLoginUserFactory.fillLoginUserInfo(sysLoginUser);
        return sysLoginUser;
    }

    /**
     * 缓存token与登录用户信息对应, 默认2个小时
     *
     * @author oderway
     * @date 2020/3/13 14:51
     */
    private void cacheLoginUser(JwtPayLoad jwtPayLoad, SysLoginUser sysLoginUser) {
        String redisLoginUserKey = jwtPayLoad.getUuid();
        Long expire_time = HandlerCommon.getPcTokenExpire();
        userCache.put(redisLoginUserKey, sysLoginUser);
    }

    /**
     * @param jwtPayLoad
     * @param sysLoginUser
     * @param time
     */
    private void cacheLoginUser(JwtPayLoad jwtPayLoad, SysLoginUser sysLoginUser, Long time) {
        String redisLoginUserKey = jwtPayLoad.getUuid();
        userCache.put(redisLoginUserKey, sysLoginUser, time);
    }

    /**
     * 同步创建用户信息
     *
     * @param syncUserParam 添加参数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserMsg(SyncUserParam syncUserParam) {
        // 基础信息（判断唯一 用户名  工号  手机号）
        this.checkParam(syncUserParam);

        // 同步用户信息
        SysUser sysUser = this.syncUserMsg(syncUserParam);

        // 同步用户的机构权限
        List<Long> grantOrgIdList = syncUserParam.getGrantOrgIdList();
        sysUserDataScopeService.addOrEditByList(grantOrgIdList, sysUser.getId());

        /**
         *  关联机构，职位 ，角色
         */
        // 同步员工信息 机构信息 职位信息
        this.syncEmpMsg(sysUser.getId(), syncUserParam);

        // 同步角色关联信息
        List<Long> roleIds = syncUserParam.getGrantRoleIdList();
        sysUserRoleService.grantRoleByIds(roleIds, sysUser.getId());

    }

    /**
     * 刷新Token存活时间
     */
    @Override
    public void flushToken() {

        // 获取当前请求的request对象
        HttpServletRequest request = HttpServletUtil.getRequest();

        // 非空判断
        if (ObjectUtil.isNotNull(request)) {
            // 获取Token信息
            String token = this.getTokenFromRequest(request);
            // 刷新token
            this.getLoginUserByToken(token);
        } else {
            throw new ServiceException(ServerExceptionEnum.SERVER_ERROR);
        }
    }

    /**
     * 子账号登入 登入成功关联用户返回Token
     */
    @Override
    public String sonLogin(String account, String password, String cType) {

        if (ObjectUtil.hasEmpty(account, password)) {
            LogManager.me().executeLoginLog(account, LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_EMPTY.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_EMPTY);
        }

        SysAccount sysAccount = sysAccountService.getAccountByAcc(account);
        //用户不存在，账号或密码错误
        if (ObjectUtil.isEmpty(sysAccount)) {
            LogManager.me().executeLoginLog(account, LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_ERROR);
        }

        String passwordBcrypt = sysAccount.getPassword();

        //验证账号密码是否正确
        if (ObjectUtil.isEmpty(passwordBcrypt) || !BCrypt.checkpw(password, passwordBcrypt)) {
            LogManager.me().executeLoginLog(sysAccount.getAccount(), LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_ERROR);
        }
        // 根据子账户id 找到对应的用户
        SysUser sysUser = sysAccountService.getUserByAccountId(sysAccount.getId());
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new AuthException(SysUserExceptionEnum.USER_NOT_EXIST);
        }

        return this.doLogin(sysUser, cType, null);
    }

    /**
     * 同步用户信息
     */
    private SysUser syncUserMsg(SyncUserParam syncUserParam) {
        SysUser sysUser = new SysUser();
        // 浅拷贝
        BeanUtil.copyProperties(syncUserParam, sysUser);
        // 填充普通用户的相关信息
        LoginUserFactory.fillUserInfo(sysUser);
        // 设置密码为BCrypt加密后的密码
        sysUser.setPassword(BCrypt.hashpw(sysUser.getPassword(), BCrypt.gensalt()));
        // 设置状态为正常
        sysUser.setStatus(CommonStatusEnum.ENABLE.getCode());
        sysUser.setId(null);
        sysUserService.save(sysUser);

        return sysUser;
    }


    /**
     * 同步员工信息
     */
    private void syncEmpMsg(Long userId, SyncUserParam syncUserParam) {
        SysEmpParam sysEmpParam = syncUserParam.getSysEmpParam();

        // 同步员工信息
        SysEmp sysEmp = new SysEmp();
        // 浅拷贝
        BeanUtil.copyProperties(sysEmpParam, sysEmp);
        sysEmp.setId(userId);
        sysEmpService.save(sysEmp);

        // 同步职位信息
        List<Long> posIdList = sysEmpParam.getPosIdList();
        sysEmpPosService.addOrEditByList(posIdList, sysEmp.getId());

        // 同步附属职位信息
        List<Dict> extIds = sysEmpParam.getExtIds();
        sysEmpExtOrgPosService.addOrEditByList(extIds, sysEmp.getId());

    }

    /**
     * 参数校验
     */
    private void checkParam(SyncUserParam syncUserParam) {
        // 用户名唯一性判断（用户表）
        sysUserService.checkAccount(syncUserParam.getAccount(), null, false);

        // 用户名唯一性判断（子账户表）
        sysAccountService.checkParam(syncUserParam.getAccount(), null, false);

        // 手机号唯一性判断
        sysUserService.checkPhone(syncUserParam.getPhone(), null, false);

        if (ObjectUtil.isNotEmpty(syncUserParam.getSysEmpParam().getJobNum())) {
            // 工号唯一性判断
            sysEmpService.checkJobNum(syncUserParam.getSysEmpParam().getJobNum(), null, false);
        }

    }

}
