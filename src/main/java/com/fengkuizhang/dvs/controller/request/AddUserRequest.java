package com.fengkuizhang.dvs.controller.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AddUserRequest {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty @Email
    private String email;
    @Size(min = 6)
    private String password;

}
