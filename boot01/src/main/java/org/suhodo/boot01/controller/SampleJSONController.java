package org.suhodo.boot01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

/*
 * Server-Client(Browser)가 통신하는 2가지 방법
 * 1) @Controller
 *     - 주소 요청이 오면 Server는 html파일을 전송한다.
 * 2) @RestController
 *     - 주소 요청이 오면 json데이터만 전송한다.
 *       (현재는 문자열을 전송했다.)
 */
@RestController
@Log4j2
public class SampleJSONController {

    @GetMapping("/helloArr")
    public String[] helloArr(){
        log.info("helloArr................");

        return new String[]{"AAA", "BBB", "CCC"};
    }
}
