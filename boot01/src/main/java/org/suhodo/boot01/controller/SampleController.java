package org.suhodo.boot01.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/ex/ex2")
    public void ex2(Model model){
        log.info("ex/ex2.....................");

        // 리스트 전달
        List<String> strList = IntStream.range(1, 10)
                                .mapToObj(i->"Data"+i)
                                .collect(Collectors.toList());
        model.addAttribute("list", strList);

        // 맵 전달 
        Map<String, String> map = new HashMap<>();
        map.put("A", "AAAA");
        map.put("B", "BBBB");
        model.addAttribute("map", map);

        class SampleDTO{
            private String p1, p2, p3;
            public String getP1(){
                return p1;
            }
            public String getP2(){
                return p2;
            }
            public String getP3(){
                return p3;
            }
        }
        
        // 클래스 객체 전달
        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.p1 = "Value -- p1";
        sampleDTO.p2 = "Value -- p2";
        sampleDTO.p3 = "Value -- p3";
        model.addAttribute("dto", sampleDTO);
    }

    @GetMapping("/ex/ex3")
    public void ex3(Model model){
        model.addAttribute("arr", new String[]{"AAA", "BBB", "CCC"});
    }

    @GetMapping("/urlex")
    public void urlex(){

    }
}
