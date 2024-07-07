package com.cicd.todoapi.config;

import com.cicd.todoapi.controller.formatter.LocalDateFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // LocalDateFormatter를 registry에 추가
        registry.addFormatter(new LocalDateFormatter());
    }

/*//     CORS 설정 : 메서드 오버라이딩 -> 시큐리티로 이전
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //CORS 적용할 URL 패턴
                .allowedOrigins("*") // origin 지정 "http://localhost:3000"
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(300) // 지정한 시간만큼 pre-flight 캐싱
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
    }
    */
}
