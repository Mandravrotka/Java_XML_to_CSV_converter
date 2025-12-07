package com.xmlconverter.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadValidator {

    public String validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "Файл пустой";
        }
        String filename = file.getOriginalFilename();
        if (filename == null) {
            return "Имя файла не указано";
        }
        if (!filename.toLowerCase().endsWith(".xml")) {
            return "Требуется XML-файл";
        }
        return null;
    }
}