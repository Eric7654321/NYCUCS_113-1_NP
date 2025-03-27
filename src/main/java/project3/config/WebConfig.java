package project3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project3.interceptor.CheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private CheckInterceptor checkInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(checkInterceptor).addPathPatterns("/**").excludePathPatterns("/search").excludePathPatterns("/download");
    }
}