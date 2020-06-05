package com.daou.controller;


import com.daou.common.ErrorCode;
import com.daou.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

/**
 * 404 not found URL 컨트롤러
 * @author pkh879
 */
@RestController
public class ExceptionController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
    public ResponseEntity<Object> error(){
        return new ResponseEntity<>(ErrorResponse.of("존재하지 않는 URL", ErrorCode.GLOBAL, HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath(){
        return "/error";
    }
}
