package com.example.demo.sdk;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "openfeign-server")
public interface ApiService {
    @GetMapping("getMsg")
    String getMsg(String id);
}
