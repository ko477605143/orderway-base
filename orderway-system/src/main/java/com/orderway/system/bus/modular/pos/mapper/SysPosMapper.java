/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.pos.mapper;

import com.orderway.system.bus.modular.pos.entity.SysPos;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统职位mapper接口
 *
 * @author oderway
 * @date 2020/3/13 16:00
 */
public interface SysPosMapper extends BaseMapper<SysPos> {


    /**
     * 根据用户id 获取对应的职位信息
     *
     * @param userId
     * @return
     */
    List<Map> getUserPos(@Param("id") Long userId);
}
