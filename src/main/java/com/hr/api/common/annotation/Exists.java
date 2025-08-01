package com.hr.api.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.hr.api.common.validators.ExistsValidator;

@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

    String message() default "{validation.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity(); // entity class

    String field() default "id"; // field to check on
}