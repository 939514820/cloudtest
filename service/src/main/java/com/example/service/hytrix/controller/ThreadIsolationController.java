package com.example.service.hytrix.controller;

import com.netflix.hystrix.HystrixThreadPool;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ThreadIsolationController {

    final static ConcurrentHashMap<String, HystrixThreadPool> threadPools = new ConcurrentHashMap<String, HystrixThreadPool>();

//    public void request(String key) {
//        if (!threadPools.containsKey(key)) {
//            threadPools.put(key, new HystrixThreadPool.HystrixThreadPoolDefault(threadPoolKey, propertiesBuilder));
//        }
//    }
}
