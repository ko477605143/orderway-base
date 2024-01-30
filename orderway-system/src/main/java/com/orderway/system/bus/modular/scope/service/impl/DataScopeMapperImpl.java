package com.orderway.system.bus.modular.scope.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orderway.system.bus.modular.scope.entity.SysDataScope;
import com.orderway.system.bus.modular.scope.mapper.DataScopeMapper;
import com.orderway.system.bus.modular.scope.param.SysDataScopeParam;
import com.orderway.system.bus.modular.scope.service.DataScopeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataScopeMapperImpl extends ServiceImpl<DataScopeMapper, SysDataScope>
implements DataScopeService {

    @Override
    public void add(SysDataScopeParam param) {

        // aaa,bbb,ccc 分割权限
        String[] scopes = param.getTId().split(",");
        // 删除旧数据
        this.baseMapper.delete(new LambdaQueryWrapper<SysDataScope>()
                .eq(SysDataScope::getSId, param.getSId())
                .eq(SysDataScope::getSType, param.getSType()));
        if(StrUtil.isBlank(param.getTId())) {
            return;
        }

        SysDataScope sysDataScope = new SysDataScope();
        BeanUtil.copyProperties(param, sysDataScope);
        // 授权数据
        Arrays.stream(scopes).forEach(
                i -> {
                    sysDataScope.setId(null);
                    sysDataScope.setTId(i);
                    this.save(sysDataScope);
                }
        );

    }

    @Override
    public String list(SysDataScopeParam scope) {
        List<SysDataScope> sysDataScopes = this.list(new LambdaQueryWrapper<SysDataScope>()
                .eq(SysDataScope::getSId, scope.getSId())
                .eq(SysDataScope::getSType, scope.getSType()));
        StringBuffer sb = new StringBuffer();

        sysDataScopes.stream().forEach(
                i-> {
                    sb.append(i.getTId());
                    sb.append(",");
                }
        );
        // 删除最后一个逗号
        return StrUtil.removeSuffix(sb, ",");
    }

    @Override
    public List<String> unify_scope_sel_(SysDataScopeParam scope) {
        List<SysDataScope> sysDataScopes = this.list(new LambdaQueryWrapper<SysDataScope>()
                .eq(SysDataScope::getSId, scope.getSId())
                .eq(SysDataScope::getSType, scope.getSType()));
        List<String> list = new ArrayList<>();
        sysDataScopes.forEach(i -> list.add(i.getTId()));
        return list;
    }
}
