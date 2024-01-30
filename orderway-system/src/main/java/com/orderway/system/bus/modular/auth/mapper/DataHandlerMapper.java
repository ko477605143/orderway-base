package com.orderway.system.bus.modular.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orderway.system.bus.modular.app.entity.SysApp;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface DataHandlerMapper extends BaseMapper {

    List<Map> sel_data_by_appid_key(String app_id, String app_key);

    void up_app_token(String app_id, String app_key, String app_token);

    void up_app_id_key(Long id,String app_id,String app_key);

}
