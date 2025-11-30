package com.example.half_step_backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // [핵심] JPA Auditing 활성화 (이게 있어야 createdAt 자동 생성됨)
@SpringBootApplication
public class HalfStepBackendApplication {

    public static void main(String[] args) {
        // 1. .env 파일 로드
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // 2. 환경변수 시스템 속성으로 설정
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        // 3. Spring Boot 실행
        SpringApplication.run(HalfStepBackendApplication.class, args);
    }

}