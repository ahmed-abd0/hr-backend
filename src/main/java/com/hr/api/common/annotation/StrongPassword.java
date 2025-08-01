package com.hr.api.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.hr.api.common.validators.PassayPasswordValidator;

@Documented
@Constraint(validatedBy = PassayPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "weak.password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}