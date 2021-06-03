package com.hyfly.milet.demo.demo_annotation.advice;

import com.hyfly.milet.demo.demo_common.exception.UserCheckedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;


/**
 * 异常处理切面
 *
 * @author hyfly
 */
@ControllerAdvice
public class CheckAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleBindException(WebExchangeBindException e) {
        return new ResponseEntity<>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserCheckedException.class)
    public ResponseEntity<String> handleUserCheckedException(UserCheckedException e) {
        return new ResponseEntity<>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    private String toStr(UserCheckedException e) {
        return e.getFieldName() + ":" + e.getFieldValue();
    }

    /**
     * 校验异常转字符串
     *
     * @param ex e
     * @return String
     */
    private String toStr(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream().map(e -> e.getField() + ":" + e.getDefaultMessage()).reduce("", (s1, s2) -> s1 + "\n" + s2);
    }

}
