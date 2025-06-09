package org.suhodo.boot01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.suhodo.boot01.dto.BoardDTO;
import org.suhodo.boot01.dto.BoardListReplyCountDTO;
import org.suhodo.boot01.dto.PageRequestDTO;
import org.suhodo.boot01.dto.PageResponseDTO;
import org.suhodo.boot01.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        
        // PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        PageResponseDTO<BoardListReplyCountDTO> responseDTO = 
            boardService.listWithReplyCount(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    // register.html을 보여주는 역할
    @GetMapping("/register")
    public void registerGET(){

    }

    // register.html에서 보내온 데이터를 처리하는 역할
    /*
     * boardDTO에 브라우저가 보내준 변수값이 저장되는 지 검증 @Valid
     * 검증과정에서 이상있으면 bindingResult에 에러가 발생
     */
    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
    
        log.info("board POST register...........");

        // 데이터를 넣는 과정에서 에러가 발생했다면
        if(bindingResult.hasErrors()){
            log.info("has errors...........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            // 브라우저한테 /board/register로 즉시 이동해 명령
            // 위의 errors값을 가지고 이동, 한번 보이면 이후에는 사라짐
            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@RequestParam(value="bno") Long bno, PageRequestDTO pageRequestDTO, Model model){
        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                        @Valid BoardDTO boardDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){
        log.info("board modify post............" + boardDTO);
        
        if(bindingResult.hasErrors()){
            log.info("has errors............");

            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", 
                                            bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?" + link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(value="bno") Long bno, RedirectAttributes redirectAttributes){
        log.info("remove post... " + bno);

        boardService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";
    }


}
