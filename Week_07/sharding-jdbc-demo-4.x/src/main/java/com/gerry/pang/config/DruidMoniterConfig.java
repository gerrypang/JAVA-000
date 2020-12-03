package com.gerry.pang.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * Druid监控配置
 */
@Configuration
public class DruidMoniterConfig {
	
	/**
	 * 配置一个管理后台的Servle
	 * @return
	 */
	@Bean
	public ServletRegistrationBean<?> statViewServlet() {
		ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
		Map<String,String> initParams = new HashMap<>();
		initParams.put("loginUsername", "admin");
		initParams.put("loginPassword", "admin");
		// 默认就是允许所有访问
		initParams.put("allow", "");
		// 黑名单的IP
		initParams.put("deny", "192.168.15.21");
		bean.setInitParameters(initParams);
		return bean;
	}
	
	/**
	 * 配置一个web监控的filter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<?> webStatFilter() {
		FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new WebStatFilter());
		Map<String, String> initParams = new HashMap<>();
		initParams.put("exclusions", "*.js,*.css,/druid/*");
		bean.setInitParameters(initParams);
		bean.setUrlPatterns(Arrays.asList("/*"));
		return bean;
	}

}
