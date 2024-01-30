/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.org.mapper;

import com.orderway.system.bus.modular.org.entity.SysOrg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.org.apache.bcel.internal.generic.DDIV;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 系统组织机构mapper接口
 *
 * @author oderway
 * @date 2020/3/13 16:03
 */
public interface SysOrgMapper extends BaseMapper<SysOrg> {

    /**
     * 根据用户id 获取组织机构信息
     * @param userId 用户id
     * @return
     */
    Map getUserOrg(@Param("id") Long userId);
}
