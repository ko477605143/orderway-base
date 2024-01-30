package com.orderway.system.bus.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ConsumerController {

    @Autowired
    ProviderFeignClient providerFeignClient;

    @GetMapping("/customer")
    public List<Map> hello() {
        return providerFeignClient.sel_all_user();
    }
}
