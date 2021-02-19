package com.example.service.hytrix.controller;

import com.example.service.hytrix.service.CollapserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    private CollapserService collapserService;

    @RequestMapping("/testColapse")
    public String testColapse(String name) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        String res = collapserService.test(name).get();
        context.close();
        return res;
    }


}