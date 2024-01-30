/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.user.service;

import com.orderway.system.bus.modular.user.entity.SysUserDataScope;
import com.orderway.system.bus.modular.user.param.SysUserParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户数据范围service接口
 *
 * @author oderway
 * @date 2020/3/13 15:45
 */
public interface SysUserDataScopeService extends IService<SysUserDataScope> {

    /**
     * 授权数据
     *
     * @param sysUserParam 授权参数
     * @author oderway
     * @date 2020/3/28 16:57
     */
    void grantData(SysUserParam sysUserParam);

    /**
     * 获取用户的数据范围id集合
     *
     * @param uerId 用户id
     * @return 数据范围id集合
     * @author oderway
     * @date 2020/4/5 17:27
     */
    List<Long> getUserDataScopeIdList(Long uerId);

    /**
     * 根据机构id集合删除对应的用户-数据范围关联信息
     *
     * @param orgIdList 机构id集合
     * @author oderway
     * @date 2020/6/28 14:15
     */
    void deleteUserDataScopeListByOrgIdList(List<Long> orgIdList);

    /**
     * 根据用户id删除对应的用户-数据范围关联信息
     *
     * @param userId 用户id
     * @author oderway
     * @date 2020/6/28 14:52
     */
    void deleteUserDataScopeListByUserId(Long userId);

    /**
     * 关联用户数据范围
     */
    void addOrEditByList(@Param("list") List<Long> orgIdList, @Param("id") Long userId);
}
