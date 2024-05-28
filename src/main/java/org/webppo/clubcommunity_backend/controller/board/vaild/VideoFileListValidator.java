package org.webppo.clubcommunity_backend.controller.board.vaild;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class VideoFileListValidator implements ConstraintValidator<ValidVideoFileList, List<MultipartFile>> {

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
        boolean isVideo = Arrays.asList("video/mp4", "video/avi", "video/mov", "video/wmv", "video/flv").contains(value.getContentType());
        boolean isSizeAcceptable = value.getSize() <= 1024 * 1024 * 100;
        return isVideo && isSizeAcceptable;
    }
}
