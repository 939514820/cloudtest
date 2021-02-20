package com.example.service.hytrix.controller;

import com.alibaba.fastjson.JSON;
import com.example.service.hytrix.CommandParam;
import com.example.service.hytrix.CommandResponse;
import com.example.service.hytrix.service.CollapserService;
import com.example.service.hytrix.service.HystrixThreadPoolService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/hytrix")
@Slf4j
public class HytrixTestController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "hiError")
    public String home(@RequestParam String name) {
        return "hi " + name + ",i am from port:" + port;
    }

    public String hiError(String name) {
        return "hi," + name + ",sorry,error!";
    }


    @HystrixCommand(groupKey = "groupTest", commandKey = "commandTest", fallbackMethod = "testFallback", commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    @RequestMapping("/test")
    public String test(ModelAndView mav, @RequestParam(defaultValue = "1") int time) {
        try {
            Thread.sleep(1000 * time);
        } catch (Exception e) {
        }
        return "success-------";
    }

    /**
     * test访问熔断后回调页面
     *
     * @param mav
     * @param time
     * @return
     */
    protected String testFallback(ModelAndView mav, @RequestParam(defaultValue = "1") int time) {
        return "熔断了";
    }

    @Resource
    private HystrixThreadPoolService hystrixThreadPoolService;

    @RequestMapping("deal")
    public String submitRequest(CommandParam param) throws ExecutionException, InterruptedException {
        CommandResponse response = hystrixThreadPoolService.submit(param);
        return JSON.toJSONString(response);
    }

}