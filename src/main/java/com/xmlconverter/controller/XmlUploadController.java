package com.xmlconverter.controller;

import com.xmlconverter.service.XmlProcessingService;
import com.xmlconverter.validator.XmlUploadValidator;
import com.xmlconverter.utils.ResponseHandler;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/xml")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
class XmlUploadController {
    XmlProcessingService xmlProcessingService;
    XmlUploadValidator xmlUploadValidator;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> uploadGamesXmlFile(@RequestParam("file") final MultipartFile file) {
        try {
            val validationError = xmlUploadValidator.validate(file);
            if (validationError != null) {
                return ResponseHandler.createErrorResponse(validationError, BAD_REQUEST);
            }

            return ResponseHandler.createSuccessResponse(xmlProcessingService.processXmlFile(file));
        } catch (Exception thrown) {
            log.error("Ошибка при обработке загрузки XML-файла", thrown);
            return ResponseHandler.createErrorResponse(
                "Ошибка обработки: %s".formatted(thrown.getMessage()),
                INTERNAL_SERVER_ERROR);
        }
    }
}