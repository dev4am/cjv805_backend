package com.fengkuizhang.dvs.controller.request;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegisterPasswordValidator implements ConstraintValidator<RegisterPasswordConstraint, RegisterRequest> {

    @Override
    public void initialize(RegisterPasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext constraintValidatorContext) {
        return registerRequest.getPassword().equals(registerRequest.getConfirmPassword());
    }

}
