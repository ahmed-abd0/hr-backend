package com.hr.api.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.hr.api.common.validators.UniqueValidator;


@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "{validation.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName(); // e.g., "username"
    
    boolean updating() default false;

    Class<?> entity(); // e.g., User.class
}