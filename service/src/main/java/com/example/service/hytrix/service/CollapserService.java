package com.example.service.hytrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
@Slf4j
@Service
public class CollapserService {
    @HystrixCollapser(batchMethod = "testAll", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {
                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "maxRequestsInBatch", value = "5")
            })
    public Future<String> test(String param) {
        return null;
    }


    @HystrixCommand
    public List<String> testAll(List<String> params) {
        log.info("合并操作线程 --> {} --> params --> {}", Thread.currentThread().getName(), params);
        List<String> result = new ArrayList<>();
        result.addAll(params);
        return result;
    }
}
