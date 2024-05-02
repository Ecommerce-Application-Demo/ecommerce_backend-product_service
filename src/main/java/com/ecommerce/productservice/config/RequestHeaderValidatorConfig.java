package com.ecommerce.productservice.config;

import com.ecommerce.productservice.interceptor.ProductRequestHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class RequestHeaderValidatorConfig implements WebMvcConfigurer {

    @Autowired
    ProductRequestHeaderInterceptor interceptor;

    String[] publicURL= {
            "/get/**",
            "/actuator/**",
            "/v3/api-docs","/swagger-ui/**","/swagger-ui.html","/swagger-resources/**","/v3/api-docs/**",
            "/swagger-ui/**","/swagger-ui/","/swagger-ui"
    };

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(interceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(publicURL);
//    }
}
