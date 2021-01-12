package com.example.demo.controller;


import com.example.demo.annotation.LogAspect;
import com.example.demo.sdk.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RestController
@Slf4j
public class TestController {

    @Resource
    private ApiService apiService;
    @GetMapping("/test")
    public String get(){
        String res =apiService.getMsg("id");
        log.info("-----------");
        return res;
    }
    @LogAspect
    @GetMapping("/test01")
    public String get01(){
        log.info("-----------");
        return "success";
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return restTemplate.getForEntity("http://OPENFEIGN-SERVER/add?a=10&b=20", String.class).getBody();
    }


}
