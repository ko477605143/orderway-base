/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.orderway.system.bus.modular.auth.util.IDCommon;
import com.orderway.system.bus.modular.user.entity.SysUserDataScope;
import com.orderway.system.bus.modular.user.mapper.SysUserDataScopeMapper;
import com.orderway.system.bus.modular.user.param.SysUserParam;
import com.orderway.system.bus.modular.user.service.SysUserDataScopeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户数据范围service接口实现类
 *
 * @author oderway
 * @date 2020/3/13 15:49
 */
@Service
public class SysUserDataScopeServiceImpl extends ServiceImpl<SysUserDataScopeMapper, SysUserDataScope>
        implements SysUserDataScopeService {

    @Override
    public void grantData(SysUserParam sysUserParam) {
        Long userId = sysUserParam.getId();
        //删除所拥有数据
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDataScope::getUserId, userId);
        this.remove(queryWrapper);
        //授权数据
        sysUserParam.getGrantOrgIdList().forEach(orgId -> {
            SysUserDataScope sysUserDataScope = new SysUserDataScope();
            sysUserDataScope.setUserId(userId);
            sysUserDataScope.setOrgId(orgId);
            this.save(sysUserDataScope);
        });
    }

    @Override
    public List<Long> getUserDataScopeIdList(Long uerId) {
        List<Long> userDataScopeIdList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDataScope::getUserId, uerId);
        this.list(queryWrapper).forEach(sysUserDataScope -> userDataScopeIdList.add(sysUserDataScope.getOrgId()));
        return userDataScopeIdList;
    }

    @Override
    public void deleteUserDataScopeListByOrgIdList(List<Long> orgIdList) {
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysUserDataScope::getOrgId, orgIdList);
        this.remove(queryWrapper);
    }

    @Override
    public void deleteUserDataScopeListByUserId(Long userId) {
        LambdaQueryWrapper<SysUserDataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDataScope::getUserId, userId);
        this.remove(queryWrapper);
    }

    /**
     * 关联用户数据范围
     */
    @Override
    public void addOrEditByList(List<Long> orgIdList, Long userId) {
        // 先删除所有关联数据
        this.deleteUserDataScopeListByUserId(userId);
        orgIdList.forEach(i -> {
            Long uuid = IDCommon.getUUID();
            this.baseMapper.add_user_data(uuid,i,userId);
        });

    }
}
