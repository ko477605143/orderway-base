/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.emp.mapper;

import cn.hutool.core.lang.Dict;
import com.orderway.system.bus.modular.emp.entity.SysEmpExtOrgPos;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 员工附属机构mapper接口
 *
 * @author oderway
 * @date 2020/3/13 15:13
 */
public interface SysEmpExtOrgPosMapper extends BaseMapper<SysEmpExtOrgPos> {

    void addExtList(@Param("id") Long id, @Param("dict") Dict dict, @Param("empId") Long empId);
}
