package com.orderway.system.bus.provider;

import com.orderway.system.bus.modular.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProviderFeignController implements ProviderFeignClient {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<Map> sel_all_user() {
        return  sysUserService.sel_user_test();
    }
}
