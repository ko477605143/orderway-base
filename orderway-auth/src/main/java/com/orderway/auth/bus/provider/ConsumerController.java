package com.orderway.auth.bus.provider;

import com.orderway.auth.bus.provider.ProviderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    ProviderFeignClient providerFeignClient;

    @GetMapping("/customer")
    public String hello() {
        return providerFeignClient.index();
    }
}
