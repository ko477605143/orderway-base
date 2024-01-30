/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.orderway.core.consts.CommonConstant;
import com.orderway.core.enums.CommonStatusEnum;
import com.orderway.core.enums.YesOrNotEnum;
import com.orderway.core.exception.ServiceException;
import com.orderway.core.exception.enums.StatusExceptionEnum;
import com.orderway.core.factory.PageFactory;
import com.orderway.core.pojo.page.PageResult;
import com.orderway.system.bus.core.enums.AdminTypeEnum;
import com.orderway.system.bus.modular.app.entity.SysApp;
import com.orderway.system.bus.modular.app.enums.SysAppExceptionEnum;
import com.orderway.system.bus.modular.app.mapper.SysAppMapper;
import com.orderway.system.bus.modular.app.param.SysAppParam;
import com.orderway.system.bus.modular.app.service.SysAppService;
import com.orderway.system.bus.modular.dict.entity.SysDictType;
import com.orderway.system.bus.modular.dict.enums.SysDictTypeExceptionEnum;
import com.orderway.system.bus.modular.dict.param.SysDictTypeParam;
import com.orderway.system.bus.modular.dict.service.SysDictTypeService;
import com.orderway.system.bus.modular.menu.service.SysMenuService;
import com.orderway.system.bus.modular.user.entity.SysUser;
import com.orderway.system.bus.modular.user.enums.SysUserExceptionEnum;
import com.orderway.system.bus.modular.user.param.SysUserParam;
import com.orderway.system.bus.modular.user.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.bouncycastle.pqc.math.linearalgebra.IntUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * 系统应用service接口实现类
 *
 * @author oderway
 * @date 2020/3/13 16:15
 */
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppMapper, SysApp> implements SysAppService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysDictTypeService sysDictTypeService;

    @Resource
    private SysAppMapper sysAppMapper;

    @Override
    public List<Dict> getLoginApps(Long userId) {
        List<Dict> userAppDictList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysApp::getStatus, CommonStatusEnum.ENABLE.getCode());


        SysUser sysUser = sysUserService.getById(userId);
        Integer adminType = sysUser.getAdminType();

        //如果不是超级管理员则有自己的菜单对应的应用编码
        if (!AdminTypeEnum.SUPER_ADMIN.getCode().equals(adminType)) {
            //获取用户菜单对应的应用编码集合
            List<String> appCodeList = sysMenuService.getUserMenuAppCodeList(userId);
            //当应用编码不为空时，则限制查询范围
            if (ObjectUtil.isNotEmpty(appCodeList)) {
                queryWrapper.in(SysApp::getCode, appCodeList);
            } else {
                //没查到应用编码则直接返回
                return userAppDictList;
            }
        }
        //定义是否有默认激活的应用标志
        AtomicBoolean hasDefaultActive = new AtomicBoolean(false);
        //遍历
        this.list(queryWrapper).forEach(sysApp -> {
            Dict dict = Dict.create();
            dict.put(CommonConstant.CODE, sysApp.getCode());
            dict.put(CommonConstant.NAME, sysApp.getName());
            //如果有默认激活的
            if (YesOrNotEnum.Y.getCode().equals(sysApp.getActive())) {
                hasDefaultActive.set(true);
                dict.put("active", true);
                //将其放在第一个
                userAppDictList.add(0, dict);
            } else {
                dict.put("active", false);
                userAppDictList.add(dict);
            }

        });
        if (ObjectUtil.isNotEmpty(userAppDictList)) {
            //如果默认激活的系统没有，则第一个为默认激活的系统
            if (!hasDefaultActive.get()) {
                Dict dict = userAppDictList.get(0);
                dict.put("active", true);
            }
        }
        return userAppDictList;
    }

    @Override
    public PageResult<SysApp> page(SysAppParam sysAppParam) {
        LambdaQueryWrapper<SysApp> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysAppParam)) {
            //根据名称模糊查询
            if (ObjectUtil.isNotEmpty(sysAppParam.getName())) {
                queryWrapper.like(SysApp::getName, sysAppParam.getName());
            }
            //根据编码模糊查询
            if (ObjectUtil.isNotEmpty(sysAppParam.getCode())) {
                queryWrapper.like(SysApp::getCode, sysAppParam.getCode());
            }
        }
        queryWrapper.eq(SysApp::getStatus, CommonStatusEnum.ENABLE.getCode())
                    .or()
                    .eq(SysApp::getStatus,CommonStatusEnum.DISABLE.getCode())
                    .orderByDesc(SysApp::getCreateTime);
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public void add(SysAppParam sysAppParam) {
        //校验参数，检查是否存在相同的名称和编码，以及默认激活的系统的数量是否合理
        checkParam(sysAppParam, false);
        SysApp sysApp = new SysApp();
        BeanUtil.copyProperties(sysAppParam, sysApp);
        sysApp.setId(null);
        sysApp.setStatus(CommonStatusEnum.ENABLE.getCode());
        String xxx = RandomStringUtils.randomNumeric(6);
        sysApp.setAppId(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + xxx);
        sysApp.setAppKey(UUID.randomUUID().toString().replace("-", ""));
        if(sysApp.getUrlType() == null) {
            sysApp.setUrlType(2);
        }
        this.save(sysApp);
    }

    @Override
    public void delete(SysAppParam sysAppParam) {
        SysApp sysApp = this.querySysApp(sysAppParam);
        String code = sysApp.getCode();
        //该应用下是否有状态为正常的菜单
        boolean hasMenu = sysMenuService.hasMenu(code);
        //只要有，则不能删
        if (hasMenu) {
            throw new ServiceException(SysAppExceptionEnum.APP_CANNOT_DELETE);
        }
        sysApp.setStatus(CommonStatusEnum.DELETED.getCode());
        this.updateById(sysApp);
    }

    @Override
    public void edit(SysAppParam sysAppParam) {
        SysApp sysApp = this.querySysApp(sysAppParam);
        //校验参数，检查是否存在相同的名称和编码，以及默认激活的系统的数量是否合理
        checkParam(sysAppParam, true);
        BeanUtil.copyProperties(sysAppParam, sysApp);
        //不能修改状态，用修改状态接口修改状态
        sysApp.setStatus(null);
        sysApp.setAppId(null);
        sysApp.setAppKey(null);
        this.updateById(sysApp);
    }

    @Override
    public SysApp detail(SysAppParam sysAppParam) {
        return this.querySysApp(sysAppParam);
    }

    @Override
    public List<SysApp> list(SysAppParam sysAppParam) {
        LambdaQueryWrapper<SysApp> appQueryWrapper = new LambdaQueryWrapper<>();
        appQueryWrapper.eq(SysApp::getStatus, CommonStatusEnum.ENABLE.getCode());
        return this.list(appQueryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setAsDefault(SysAppParam sysAppParam) {
        SysApp currentApp = this.querySysApp(sysAppParam);
        //所有已激活的改为未激活
        LambdaQueryWrapper<SysApp> appQueryWrapper = new LambdaQueryWrapper<>();
        appQueryWrapper.eq(SysApp::getStatus, CommonStatusEnum.ENABLE.getCode())
                .eq(SysApp::getActive, YesOrNotEnum.Y.getCode());
        this.list(appQueryWrapper).forEach(sysApp -> {
            sysApp.setActive(YesOrNotEnum.N.getCode());
            this.updateById(sysApp);
        });
        //当前的设置为已激活
        currentApp.setActive(YesOrNotEnum.Y.getCode());
        this.updateById(currentApp);
    }

    /**
     * 校验参数，检查是否存在相同的名称和编码，以及默认激活的系统的数量是否合理
     *
     * @author oderway
     * @date 2020/3/25 21:23
     */
    private void checkParam(SysAppParam sysAppParam, boolean isExcludeSelf) {
        Long id = sysAppParam.getId();
        String name = sysAppParam.getName();
        String code = sysAppParam.getCode();
        String active = sysAppParam.getActive();

        // 查询名称有无重复
        LambdaQueryWrapper<SysApp> appQueryWrapperByName = new LambdaQueryWrapper<>();
        appQueryWrapperByName.eq(SysApp::getName, name)
                .ne(SysApp::getStatus, CommonStatusEnum.DELETED.getCode());

        // 查询编码有无重复
        LambdaQueryWrapper<SysApp> appQueryWrapperByCode = new LambdaQueryWrapper<>();
        appQueryWrapperByCode.eq(SysApp::getCode, code)
                .ne(SysApp::getStatus, CommonStatusEnum.DELETED.getCode());

        // 查询激活状态有无已经有Y的，也就是激活的
        LambdaQueryWrapper<SysApp> appQueryWrapperByActive = new LambdaQueryWrapper<>();
        appQueryWrapperByActive.eq(SysApp::getActive, active)
                .ne(SysApp::getStatus, CommonStatusEnum.DELETED.getCode());

        if (isExcludeSelf) {
            appQueryWrapperByName.ne(SysApp::getId, id);
            appQueryWrapperByCode.ne(SysApp::getId, id);
            appQueryWrapperByActive.ne(SysApp::getId, id);
        }
        int countByName = this.count(appQueryWrapperByName);
        int countByCode = this.count(appQueryWrapperByCode);
        int countByActive = this.count(appQueryWrapperByActive);

        if (countByName >= 1) {
            throw new ServiceException(SysAppExceptionEnum.APP_NAME_REPEAT);
        }
        if (countByCode >= 1) {
            throw new ServiceException(SysAppExceptionEnum.APP_CODE_REPEAT);
        }

        // 只判断激活状态为Y时候数量是否大于1了
        if (countByActive >= 1 && YesOrNotEnum.Y.getCode().equals(sysAppParam.getActive())) {
            throw new ServiceException(SysAppExceptionEnum.APP_ACTIVE_REPEAT);
        }
    }

    /**
     * 获取系统应用
     *
     * @author oderway
     * @date 2020/3/26 9:56
     */
    private SysApp querySysApp(SysAppParam sysAppParam) {
        SysApp sysApp = this.getById(sysAppParam.getId());
        if (ObjectUtil.isNull(sysApp)) {
            throw new ServiceException(SysAppExceptionEnum.APP_NOT_EXIST);
        }
        return sysApp;
    }

    /**
     * 获取客户端类型
     */
    @Override
    public List<Map> appDropDown(SysDictTypeParam sysDictTypeParam) {
        LambdaQueryWrapper<SysDictType> qw = new LambdaQueryWrapper<>();
        qw.eq(SysDictType::getCode, sysDictTypeParam.getCode());

        SysDictType sysDictType = sysDictTypeService.getOne(qw);
        if (ObjectUtil.isNull(sysDictType)) {
            throw new ServiceException(SysDictTypeExceptionEnum.DICT_TYPE_NOT_EXIST);
        }
        return this.baseMapper.getDictCodeAndValueByDictTypeId(sysDictType.getId());
    }


    /**
     * 查询应用管理信息并分页
     */
    @Override
    public PageResult<Map> selectAppByPage(SysAppParam sysAppParam) {
        QueryWrapper<SysApp> qw = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(sysAppParam)) {
            //根据名称模糊查询
            if (ObjectUtil.isNotEmpty(sysAppParam.getName())) {
                qw.like("t1.name", sysAppParam.getName());
            }
            //根据编码模糊查询
            if (ObjectUtil.isNotEmpty(sysAppParam.getCode())) {
                qw.like("t1.code", sysAppParam.getCode());
            }
        }
        //  状态为正常的应用/停用的应用
        qw.and(i -> i.eq("t1.status", CommonStatusEnum.ENABLE.getCode()).or().eq("t1.status",CommonStatusEnum.DISABLE.getCode()))
        .orderByDesc("create_time");
//        qw.eq("t1.status", CommonStatusEnum.ENABLE.getCode()).or().eq("t1.status",CommonStatusEnum.DISABLE.getCode());
        return new PageResult<>(this.baseMapper.page(PageFactory.defaultPage(), qw));
    }

    @Override
    public List<Map<String, Object>> selectAll() {
        //return this.listMaps(new LambdaQueryWrapper<SysApp>()
        //        .select(SysApp::getAppId, SysApp::getName)
        //        .eq(SysApp::getStatus, CommonStatusEnum.ENABLE.getCode()));
        return sysAppMapper.get_all_app();
    }

    @Override
    public SysApp getAllByRoleId(Long role) {
        return  this.baseMapper.getAllByRoleId(role);
    }

    @Override
    public List<SysApp> getAllByRoleIds(List<Long> roleIds) {
        return this.baseMapper.getAllByRoleIds(roleIds);
    }

    /**
     * 根据登入用户 获取所有应用信息
     */
    @Override
    public List<Dict> getAllApp(List<Long> list) {
        // 角色集合为空，则根本角色
        if (list == null || list.size() == 0) {
            // 返回空集合
            return CollectionUtil.newArrayList();
        }
        //
        List<Dict> dictList = CollectionUtil.newArrayList();
        // 获取所有的角色
        List<SysApp> allByRoleIds = this.getAllByRoleIds(list);
        //定义是否有默认激活的应用标志
        AtomicBoolean hasDefaultActive = new AtomicBoolean(false);

        allByRoleIds.forEach(i -> {
            if (ObjectUtil.isNotEmpty(i)) {
                Dict dict = Dict.create();
                dict.put(CommonConstant.CODE, i.getCode());
                dict.put(CommonConstant.NAME, i.getName());
                //如果有默认激活的
                if (YesOrNotEnum.Y.getCode().equals(i.getActive())) {
                    hasDefaultActive.set(true);
                    dict.put("active", true);
                    //将其放在第一个
                    dictList.add(0, dict);

                } else {
                    dict.put("active", false);
                    dictList.add(dict);

                }
            }
        });

        if (ObjectUtil.isNotEmpty(dictList)) {
            //如果默认激活的系统没有，则第一个为默认激活的系统
            if (!hasDefaultActive.get()) {
                Dict dict = dictList.get(0);
                dict.put("active", true);
            }
        }
        return dictList;
    }

    @Override
    public List<Map> getAllAppByUserId(long userId) {
        return this.baseMapper.getAllAppByUserId(userId);
    }

    /**
     * 根据应用编码 获取应用信息
     */
    @Override
    public Map getAppInfo(String app) {
        LambdaQueryWrapper<SysApp> qw = new LambdaQueryWrapper<>();
        qw.select(SysApp::getId, SysApp::getName, SysApp::getActive, SysApp::getCode, SysApp::getClientType)
                .eq(SysApp::getStatus, CommonStatusEnum.ENABLE.getCode())
                .eq(SysApp::getCode, app);
        Map<String, Object> sysApp = this.getMap(qw);
        return sysApp;
    }

    /**
     * 判断有没有该应用编码的权限
     */
    @Override
    public Integer checkCode(String code, Long userId) {
        return this.baseMapper.checkCode(code, userId);
    }

    @Override
    public void changeStatus(SysAppParam sysAppParam) {
        SysApp sysApp = this.querySysUser(sysAppParam);
        //不能修改超级管理员状态
//        if (AdminTypeEnum.SUPER_ADMIN.getCode().equals(sysUser.getAdminType())) {
//            throw new ServiceException(SysUserExceptionEnum.USER_CAN_NOT_UPDATE_ADMIN);
//        }

        Long id = sysApp.getId();

        Integer status = sysAppParam.getStatus();
        //校验状态在不在枚举值里
        CommonStatusEnum.validateStatus(status);

        //更新枚举，更新只能更新未删除状态的
        LambdaUpdateWrapper<SysApp> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysApp::getId, id)
                .and(i -> i.ne(SysApp::getStatus, CommonStatusEnum.DELETED.getCode()))
                .set(SysApp::getStatus, status);
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new ServiceException(StatusExceptionEnum.UPDATE_STATUS_ERROR);
        }
    }

    /**
     * 获取系统应用
     */
    private SysApp querySysUser(SysAppParam sysAppParam) {
        SysApp sysApp = this.getById(sysAppParam.getId());
        if (ObjectUtil.isNull(sysApp)) {
            throw new ServiceException(SysUserExceptionEnum.USER_NOT_EXIST);
        }
        return sysApp;
    }
}
