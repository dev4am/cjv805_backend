package com.fengkuizhang.dvs.exception;

import com.fengkuizhang.dvs.controller.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity handleValidationExceptions(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuffer message = new StringBuffer();
        for(ObjectError error : result.getAllErrors()){
            if(error instanceof FieldError){
                message.append(String.format("%s, ", error.getDefaultMessage()));
            }else if(error instanceof ObjectError){
                message.append(String.format("%s, ", error.getDefaultMessage()));
            }
        }
        return new ResponseEntity(Response
                .builder()
                .msg(message.toString())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    public final ResponseEntity handleAppException(AppException e) {
        return new ResponseEntity(Response
                .builder()
                .msg(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
