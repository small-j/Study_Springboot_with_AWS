package com.smallj.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//JPA Auditing 활성화
@EnableJpaAuditing
//@Component Scan 어노테이션을 가지고 있는 어노테이션.
//이 파일이 있는 위치에서 하위의 클래스 중 빈을 만들어줘야하는 @Component어노테이션을 가진 클래스를 찾는다.
@SpringBootApplication
public class Application {
    //메인클래스
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
