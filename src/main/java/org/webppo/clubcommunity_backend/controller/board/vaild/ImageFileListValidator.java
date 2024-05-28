package org.webppo.clubcommunity_backend.controller.board.vaild;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageFileListValidator implements ConstraintValidator<ValidImageFileList, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> values, ConstraintValidatorContext context) {
        if (values == null) return false;

        for (MultipartFile value : values) {
            if (value == null || !isValidFile(value)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidFile(MultipartFile value) {
        boolean isImage = Arrays.asList("image/jpg", "image/jpeg", "image/gif", "image/bmp", "image/png").contains(value.getContentType());
        boolean isSizeAcceptable = value.getSize() <= 1024 * 1024 * 10;
        return isImage && isSizeAcceptable;
    }
}
