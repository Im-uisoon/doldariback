package com.project.doldariserver.config;

import jakarta.servlet.SessionCookieConfig;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://im-uisoon.github.io") // 배포 프론트 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true) // 쿠키 전달 허용
                .maxAge(28800);
    }

    // 세션 쿠키 설정
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
            sessionCookieConfig.setHttpOnly(true);          // JS 접근 불가
            sessionCookieConfig.setSecure(true);            // HTTPS 환경에서만
            sessionCookieConfig.setName("JSESSIONID");      // 기본 세션 이름
            sessionCookieConfig.setPath("/");               // 전체 경로에서 쿠키 전달
            sessionCookieConfig.setMaxAge(-1);              // 브라우저 종료 시까지
        };
    }
}