package com.xmlconverter.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;

@Component
public class UploadValidator {

    public String validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "Файл пустой";
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            return "Имя файла не указано";
        }
        return null;
    }
}