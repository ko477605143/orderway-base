/*
project create by  orderway  and time 20220909
 */
package com.orderway.auth.bus.modular.dict.mapper;

import com.orderway.auth.bus.modular.dict.entity.SysDictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 系统字典值mapper接口
 *
 * @author oderway
 * @date 2020/3/13 16:12
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 通过字典类型code获取字典编码值列表
     *
     * @param dictTypeCodes 字典类型编码集合
     * @return 字典编码值列表
     * @author fengshuonan
     * @date 2020/8/9 14:27
     */
    List<String> getDictCodesByDictTypeCode(String[] dictTypeCodes);

}
