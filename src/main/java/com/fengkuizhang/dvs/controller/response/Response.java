package com.fengkuizhang.dvs.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response {

    private String msg = "";
    private Object data;

    public static Response success(String msg, Object data){
        return Response.builder().data(data).msg(msg).build();
    }

    public static Response fail(String msg){
        return Response.builder().msg(msg).build();
    }

}
