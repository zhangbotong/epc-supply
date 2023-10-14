package com.cbim.epc.supply.app;

import com.cbim.uc.sdk.EnableUcClient;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan("com.cbim.*")
@MapperScan("com.cbim.epc.supply.*.mapper")
@MapperScan("com.cbim.epc.sdk.mq.mapper")
@EnableFeignClients(basePackages = "com.cbim.epc")
@EnableUcClient
public class SupplyAppMain {
    public static void main(String[] args) {
        SpringApplication.run(SupplyAppMain.class,args);
        log.info("SupplyAppMain application started");
    }

    /**
     * <a href="http://localhost:8081/actuator/health/">Prometheus</a>
     */
    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name:Default}") String applicationName) {
        return (registry) -> registry
                .config()
                .commonTags("application", applicationName);
    }
}