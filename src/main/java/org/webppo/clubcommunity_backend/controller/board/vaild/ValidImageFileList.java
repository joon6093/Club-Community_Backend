package org.webppo.clubcommunity_backend.controller.board.vaild;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageFileListValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageFileList {
    String message() default "Invalid image files";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
