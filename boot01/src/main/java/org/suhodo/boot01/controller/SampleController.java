package org.suhodo.boot01.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

// 브라우저에서 요청하는 주소를 연결하는 메서드가 존재하는 클래스스
@Controller
@Log4j2
public class SampleController {

    // 브라우저에서 http://localhost:9090/hello 하면 이 메서드가 호출
    @GetMapping("/hello")
    public void hello(Model model){
        log.info("hello.................");

        // templates폴더에 hello.html을 찾아서 
        // 키는 msg, 값은 "HELLO WORLD"를 전달한다.
        model.addAttribute("msg", "HELLO WORLD");
    }

    @GetMapping("/ex/ex1")
    public void ex1(Model model){
        List<String> list = Arrays.asList("AAA", "BBB", "CCC", "DDD");

        model.addAttribute("list", list);
    }
}
