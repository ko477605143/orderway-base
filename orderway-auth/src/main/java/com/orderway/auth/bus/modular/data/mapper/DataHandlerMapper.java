package com.orderway.auth.bus.modular.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface DataHandlerMapper extends BaseMapper {

    List<Map> sel_data_by_appid_key(String app_id, String app_key);

    void up_app_token(String app_id, String app_key, String app_token);

    Map sel_app_by_appId(@Param("appId") String appId);

    List<Map> getAppUrl();

}
