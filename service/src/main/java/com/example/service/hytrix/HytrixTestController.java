package com.example.service.hytrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/hytrix")
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
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
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
}