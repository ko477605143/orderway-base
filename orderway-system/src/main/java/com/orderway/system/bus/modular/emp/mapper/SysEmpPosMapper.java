/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.emp.mapper;

import com.orderway.system.bus.modular.emp.entity.SysEmpPos;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 员工职位mapper接口
 *
 * @author oderway
 * @date 2020/3/13 15:12
 */
public interface SysEmpPosMapper extends BaseMapper<SysEmpPos> {

    /**
     * 新增员工职位信息
     *
     * @param id  主键
     * @param postId 职位id
     * @param empId 员工id
     */
    void addList(@Param("id") Long id, @Param("empId") Long empId, @Param("postId") Long postId);
}
