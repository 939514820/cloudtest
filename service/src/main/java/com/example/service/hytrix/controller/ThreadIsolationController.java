package com.example.service.hytrix.controller;

import com.example.service.hytrix.service.CollapserService;
import com.netflix.hystrix.HystrixThreadPool;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@RestController
public class ThreadIsolationController {

    final static ConcurrentHashMap<String, HystrixThreadPool> threadPools = new ConcurrentHashMap<String, HystrixThreadPool>();

    @Resource
    private CollapserService collapserService;

    @RequestMapping("/testColapse")
    public String testColapse(String name) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        String res = collapserService.test(name).get();
        context.close();
        return res;
    }

}
