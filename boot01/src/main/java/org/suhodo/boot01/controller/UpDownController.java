package org.suhodo.boot01.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.suhodo.boot01.dto.UploadFileDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class UpDownController {

    @Operation(description = "POST 방식으로 파일 업로드")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadFileDTO> upload(UploadFileDTO uploadFileDTO){
        
        log.info(uploadFileDTO);

        return null;
    }
}
