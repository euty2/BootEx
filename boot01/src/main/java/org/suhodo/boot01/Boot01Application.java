package org.suhodo.boot01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // BaseEntity의 새로 기록 날짜, 수정 날짜 이벤트를 수신
@SpringBootApplication
public class Boot01Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot01Application.class, args);
	}

}
