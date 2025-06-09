package org.suhodo.boot01.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// SpringBoot한테 이 클래스가 정의 클래스인 것을 알려줌
@Configuration
public class RootConfig {

    // 아래 메서드가 자동 호출되면서 modelMapaper객체를 Spring Container의 Bean으로 올려놓는다.
    // 언제든지 꺼내서 사용할 수 있도록
    // Board <-> BoardDao를 변환할 때 사용할 예정
    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
