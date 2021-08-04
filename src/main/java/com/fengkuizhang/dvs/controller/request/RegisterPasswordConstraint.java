package com.fengkuizhang.dvs.controller.request;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Constraint(validatedBy = {RegisterPasswordValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterPasswordConstraint {

    String message() default "password not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
