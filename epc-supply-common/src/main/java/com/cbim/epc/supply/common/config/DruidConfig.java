package com.cbim.epc.supply.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.cbim.epc.supply.common.config.filter.DruidAdRemoveFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    /**
     * 创建 DruidAdRemoveFilter 过滤器，过滤 common.js 的广告
     */
    @Bean
    public FilterRegistrationBean<DruidAdRemoveFilter> druidAdRemoveFilterFilter(DruidStatProperties properties) {
        // 获取 druid web 监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取 common.js 的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
        // 创建 DruidAdRemoveFilter Bean
        FilterRegistrationBean<DruidAdRemoveFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DruidAdRemoveFilter());
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }
}
