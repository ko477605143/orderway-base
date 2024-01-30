package com.orderway.system.bus.modular.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orderway.core.context.constant.ConstantContextHolder;
import com.orderway.core.enums.CommonStatusEnum;
import com.orderway.core.exception.ServiceException;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.system.bus.modular.account.entity.SysAccount;
import com.orderway.system.bus.modular.account.enums.SysAccountExceptionEnum;
import com.orderway.system.bus.modular.account.mapper.SysAccountMapper;
import com.orderway.system.bus.modular.account.param.SysAccountParam;
import com.orderway.system.bus.modular.account.service.SysAccountService;
import com.orderway.system.bus.modular.account.service.SysUserAccountService;
import com.orderway.system.bus.modular.auth.handler.HandlerNewVService;
import com.orderway.system.bus.modular.auth.service.AuthService;
import com.orderway.system.bus.modular.user.entity.SysUser;
import com.orderway.system.bus.modular.user.enums.SysUserExceptionEnum;
import com.orderway.system.bus.modular.user.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount>
        implements SysAccountService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserAccountService sysUserAccountService;

    @Resource
    private HandlerNewVService handlerNewVService;

    @Resource
    private AuthService authService;

    /**
     * 新增到账户表
     *
     * @param sysAccountParam
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysAccountParam sysAccountParam) {

        // 判断用户名 是否重复 （用户表）
        sysUserService.checkAccount(sysAccountParam.getAccount(), null, false);
        // 判断用户名 是否重复 （账户表）
        this.checkAccount(sysAccountParam, false);
        // 判断code是否重复
        this.checkCode(sysAccountParam, false);

        // 构造方法
        SysAccount sysAccount = this.passwordEncrypt(sysAccountParam);

        this.save(sysAccount);

        // 添加到关联表
        sysUserAccountService.add(sysAccount.getId(), sysAccountParam.getUserId());
    }

    /**
     * 查询用户对应多账号信息
     */
    @Override
    public List<Map> listByToken() {
        SysLoginUser loginUser = handlerNewVService.getLoginUser();
        return this.baseMapper.list(loginUser.getId());
    }

    @Override
    public List<Map> listById(Long userId) {
        return this.baseMapper.list(userId);
    }

    @Override
    public void edit(SysAccountParam sysAccountParam) {

        Long accountId = sysAccountParam.getId();
        // 根据子账号id 获取对应的子账号信息
        SysAccount account = this.getAccount(accountId);

        // 判断用户名 是否重复 （用户表）
        sysUserService.checkAccount(sysAccountParam.getAccount(), sysAccountParam.getUserId(), true);
        // 判断用户名 是否重复 （账户表）
        this.checkAccount(sysAccountParam, true);
        // 判断code是否重复
        this.checkCode(sysAccountParam, true);

        // 浅拷贝
        BeanUtil.copyProperties(sysAccountParam, account);
        //不能修改状态，用修改状态接口修改状态
        account.setStatus(null);
        // 密码不在这里修改 在修改密码接口修改
        account.setPassword(null);
        //修改子账户信息
        this.updateById(account);
    }

    /**
     * 获取子账号信息
     *
     * @param accountId 子账号id
     * @return
     */
    @Override
    public SysAccount getAccount(Long accountId) {
        SysAccount sysAccount = this.getById(accountId);
        if (ObjectUtil.isEmpty(sysAccount)) {
            throw new ServiceException(SysAccountExceptionEnum.ACCOUNT_NOT_EXIST);
        }
        return sysAccount;
    }

    /**
     * 根据子账户id 修改密码
     *
     * @param sysAccountParam 参数信息
     */
    @Override
    public void updatePwd(SysAccountParam sysAccountParam) {

        // 新密码与原密码相同
        if (sysAccountParam.getPassword().equals(sysAccountParam.getNewPassword())) {
            throw new ServiceException(SysAccountExceptionEnum.ACCOUNT_PWD_REPEAT);
        }

        SysAccount account = this.getAccount(sysAccountParam.getId());
        // 原密码错误
        if (!BCrypt.checkpw(sysAccountParam.getPassword(), account.getPassword())) {
            throw new ServiceException(SysAccountExceptionEnum.ACCOUNT_PASSWORD_ERR);
        }

        account.setPassword(BCrypt.hashpw(sysAccountParam.getNewPassword(), BCrypt.gensalt()));
        this.updateById(account);
    }

    /**
     * 重置子账号密码为 123456
     *
     * @param accountId
     */
    @Override
    public void resetPwd(Long accountId) {
        SysAccount account = this.getAccount(accountId);
        if (ObjectUtil.isEmpty(account)) {
            throw new ServiceException(SysAccountExceptionEnum.ACCOUNT_NOT_EXIST);
        }
        String password = ConstantContextHolder.getDefaultPassWord();
        account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        this.updateById(account);
    }

    /**
     * 删除子账号信息
     *
     * @param sysAccountParam
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(SysAccountParam sysAccountParam) {

        SysAccount sysAccount = new SysAccount();
        sysAccount.setId(sysAccountParam.getId());
        // 将状态设置为删除
        sysAccount.setStatus(CommonStatusEnum.DELETED.getCode());
        this.updateById(sysAccount);

        // 联级删除子账户关系表数据
        sysUserAccountService.delete(sysAccountParam.getId(), sysAccountParam.getUserId());
    }

    @Override
    public SysAccount getAccountByAcc(String account) {

        LambdaQueryWrapper<SysAccount> qw = new LambdaQueryWrapper<>();
        qw.eq(SysAccount::getAccount, account).ne(SysAccount::getStatus, CommonStatusEnum.DELETED.getCode());
        return this.getOne(qw);
    }

    @Override
    public SysUser getUserByAccountId(Long accountId) {
        return this.baseMapper.UserByAccountId(accountId);
    }

    @Override
    public void checkParam(String account, Long id, boolean isExcludeSelf) {
        SysAccountParam sysAccountParam = new SysAccountParam();
        sysAccountParam.setAccount(account);
        sysAccountParam.setId(id);

        this.checkAccount(sysAccountParam, isExcludeSelf);
    }

    /**
     * 校验参数，检查是否存在相同的账号（账户表）
     *
     * @param param         参数信息
     * @param isExcludeSelf 为false 不排除自己
     */
    private void checkAccount(SysAccountParam param, boolean isExcludeSelf) {

        LambdaQueryWrapper<SysAccount> qw = new LambdaQueryWrapper();
        // 判断account 在 sys_account 表中是否存在 ，状态不为删除
        qw.eq(SysAccount::getAccount, param.getAccount()).ne(SysAccount::getStatus, CommonStatusEnum.DELETED.getCode());
        //是否排除自己，如果是则查询条件排除自己id
        if (isExcludeSelf) {
            qw.ne(SysAccount::getId, param.getId());
        }
        int count = this.count(qw);
        if (count >= 1) {
            throw new ServiceException(SysUserExceptionEnum.USER_ACCOUNT_REPEAT);
        }
    }

    /**
     * @param param 参数信息
     *              校验参数，检查账号表是否存在相同的code
     */
    private void checkCode(SysAccountParam param, boolean isExcludeSelf) {

        LambdaQueryWrapper<SysAccount> qw = new LambdaQueryWrapper();
        // 判断code 在 sys_account 表中是否存在 ，状态不为删除
        qw.eq(SysAccount::getCode, param.getCode()).ne(SysAccount::getStatus, CommonStatusEnum.DELETED.getCode());
        //是否排除自己，如果是则查询条件排除自己id
        if (isExcludeSelf) {
            qw.ne(SysAccount::getId, param.getId());
        }
        int count = this.count(qw);
        if (count >= 1) {
            throw new ServiceException(SysAccountExceptionEnum.ACCOUNT_CODE_REPEAT);
        }
    }

    /**
     * 密码加密
     *
     * @return 账户表信息
     */
    private SysAccount passwordEncrypt(SysAccountParam sysAccountParam) {
        SysAccount sysAccount = new SysAccount();

        // 浅拷贝
        BeanUtil.copyProperties(sysAccountParam, sysAccount);

        // 密码加密
        sysAccount.setPassword(BCrypt.hashpw(sysAccount.getPassword(), BCrypt.gensalt()));

        // 状态设置为正常
        sysAccount.setStatus(CommonStatusEnum.ENABLE.getCode());

        sysAccount.setId(null);

        return sysAccount;
    }
}
