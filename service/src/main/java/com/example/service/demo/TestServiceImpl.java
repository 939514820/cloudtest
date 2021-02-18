package com.example.service.demo;

import com.example.demo.sdk.ApiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestServiceImpl implements ApiService {
    @PostMapping("getMsg")
    @Override
    public String getMsg(String id) {
        return "success...";
    }
    public String getMsg1(String id) {
        return "success...";
    }
}
