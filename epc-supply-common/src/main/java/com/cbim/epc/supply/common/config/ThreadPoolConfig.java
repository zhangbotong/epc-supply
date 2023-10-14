package com.cbim.epc.supply.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    @Primary
    @Bean("epcThreadPool")
    public Executor EpcThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //队列容量
        executor.setQueueCapacity(20);
        //活跃时间
        executor.setKeepAliveSeconds(60);
        //线程名字前缀
        executor.setThreadNamePrefix("epc-template-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // 停机是否等待任务
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 停机等待任务的最大时长
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }

    @Bean("epcSupplyThreadPool")
    public Executor epcSupplyThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //队列容量
        executor.setQueueCapacity(20);
        //活跃时间
        executor.setKeepAliveSeconds(60);
        //线程名字前缀
        executor.setThreadNamePrefix("epc-supply-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // 停机是否等待任务
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 停机等待任务的最大时长
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }
}
