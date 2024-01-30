package com.orderway.auth.bus.provider;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderFeignController implements ProviderFeignClient {
    @Override
    public String index() {
        return "天天天";
    }
}
