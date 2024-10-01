package com.indian.indian.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");
        return registrationBean;
    }

    // @Bean
    public FilterRegistrationBean<MasterAdminFilter> AdminMasterFilter() {
        FilterRegistrationBean<MasterAdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MasterAdminFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
