package com.bc.third.pay.web.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-11
 * Time:  下午 4:24.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }


//    @Bean
//    public ServletRegistrationBean druidStatViewServlet(){
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//        registrationBean.addInitParameter("loginUsername","mamahao");
//        registrationBean.addInitParameter("loginPassword","mamahao123");
//        registrationBean.addInitParameter("resetEnable","false");
//
//        return registrationBean;
//    }
}
