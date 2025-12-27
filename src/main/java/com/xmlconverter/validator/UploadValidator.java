package com.xmlconverter.validator;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;

@Component
public class UploadValidator {
    public String validateFile(@NonNull final MultipartFile file) {
        if (file.isEmpty()) {
            return "Файл пустой";
        }

        if (StringUtils.isBlank(file.getOriginalFilename())) {
            return "Имя файла не указано";
        }

        return null;
    }
}