package org.webppo.clubcommunity_backend.controller.board.vaild;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VideoFileListValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVideoFileList {
    String message() default "Invalid video files";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
