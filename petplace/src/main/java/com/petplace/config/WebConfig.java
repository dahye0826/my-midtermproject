package com.petplace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 해당 클래스는 자바에서 설정 파일로 인식합니다.
public class WebConfig implements WebMvcConfigurer {
    // WebMvcConfigurer : 웹 애플리케이션 설정용으로 사용하는 인터페이스

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/post/**")
                .addResourceLocations("file:///C:/petImage/images/post/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 요청에 CORS 적용
                .allowedOrigins("http://localhost:3000") //프론트 포트
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                 .allowCredentials(true)
                .maxAge(3600);
;
    }
}