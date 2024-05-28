package org.webppo.clubcommunity_backend.controller.board.vaild;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class ImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) return false;
        boolean isImage = Arrays.asList("image/jpg", "image/jpeg", "image/gif", "image/bmp", "image/png").contains(value.getContentType());
        boolean isSizeAcceptable = value.getSize() <= 1024 * 1024 * 10;
        return isImage && isSizeAcceptable;
    }
}
