package com.orderway.system.bus.modular.scope.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orderway.system.bus.modular.scope.entity.SysDataScope;
import com.orderway.system.bus.modular.scope.mapper.DataScopeMapper;
import com.orderway.system.bus.modular.scope.param.SysDataScopeParam;

import java.util.List;

public interface DataScopeService extends IService<SysDataScope> {

    void add(SysDataScopeParam scope);

    String list(SysDataScopeParam scope);

    List<String> unify_scope_sel_(SysDataScopeParam scope);
}
