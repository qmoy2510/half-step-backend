package com.example.half_step_backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HalfStepBackendApplication {

    public static void main(String[] args) {
        // 1. .env 파일 로드 (없으면 무시하도록 ignoreIfMissing 설정 가능)
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // 2. 로드한 환경변수를 시스템 속성으로 설정 (Spring이 읽을 수 있게 함)
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        // 3. Spring Boot 실행
        SpringApplication.run(HalfStepBackendApplication.class, args);
    }

}
