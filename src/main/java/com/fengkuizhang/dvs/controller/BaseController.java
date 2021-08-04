package com.fengkuizhang.dvs.controller;

import com.fengkuizhang.dvs.controller.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity success(){
        return success(null);
    }

    protected ResponseEntity success(Object data){
        ResponseEntity responseEntity = new ResponseEntity(Response.success("", data), HttpStatus.OK);
        return responseEntity;
    }

    protected ResponseEntity fail(HttpStatus status, String msg){
        ResponseEntity responseEntity = new ResponseEntity(Response.fail(msg), status);
        return responseEntity;
    }

}
