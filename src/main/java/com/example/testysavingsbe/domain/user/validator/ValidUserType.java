package com.example.testysavingsbe.domain.user.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * 유저의 선호 타입을 판별하는 어노테이션
*/
@Documented
@Constraint(validatedBy = UserTypesValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserType {
    String message() default "유효하지 않은 타입입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
