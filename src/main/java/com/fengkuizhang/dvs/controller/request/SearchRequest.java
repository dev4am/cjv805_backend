package com.fengkuizhang.dvs.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SearchRequest {

    @NotEmpty
    private String type;
    @NotEmpty
    private String q;

}
