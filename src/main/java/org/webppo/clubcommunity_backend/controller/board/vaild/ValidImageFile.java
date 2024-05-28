package org.webppo.clubcommunity_backend.controller.board.vaild;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageFileValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageFile {
    String message() default "Invalid image";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
