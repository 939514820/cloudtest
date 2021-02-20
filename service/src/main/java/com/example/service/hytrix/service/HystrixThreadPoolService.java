package com.example.service.hytrix.service;

import com.alibaba.fastjson.JSON;
import com.example.service.hytrix.CommandParam;
import com.example.service.hytrix.CommandResponse;
import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class HystrixThreadPoolService {
    final int coreSize = 2, maximumSize = 5, maxQueueSize = 10;
    final String commandName = "TestThreadPoolCommand";

    public HystrixCommand.Setter getPoolBasicConfig(String commandName) {
        HystrixCommand.Setter commandConfig = HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandName))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties
                                .Setter()
                                .withExecutionTimeoutEnabled(false))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties
                                .Setter()
                                .withCoreSize(coreSize)
                                .withMaximumSize(maximumSize)
                                .withAllowMaximumSizeToDivergeFromCoreSize(true)
                                .withMaxQueueSize(maxQueueSize)
                                .withQueueSizeRejectionThreshold(maxQueueSize));
        return commandConfig;
    }

    public  CommandResponse submit(CommandParam param) throws ExecutionException, InterruptedException {
        // Run command once, so we can get metrics.
        HystrixCommand<CommandResponse> command = new HystrixCommand<CommandResponse>(getPoolBasicConfig(String.valueOf(param.getProductId()))) {
            @Override
            protected CommandResponse run() throws Exception {
                //
                log.info("请求第三方接口----{}", JSON.toJSONString(param));
                Thread.sleep(1000);
                return new CommandResponse();
            }
        };
        Future<CommandResponse> res = command.queue();
        printThreadPoolStatus();
        return res.get();
    }

    static void printThreadPoolStatus() {
        for (HystrixThreadPoolMetrics threadPoolMetrics : HystrixThreadPoolMetrics.getInstances()) {
            String name = threadPoolMetrics.getThreadPoolKey().name();
            Number poolSize = threadPoolMetrics.getCurrentPoolSize();
            Number queueSize = threadPoolMetrics.getCurrentQueueSize();
            System.out.println("ThreadPoolKey: " + name + ", PoolSize: " + poolSize + ", QueueSize: " + queueSize);
        }

    }
}
