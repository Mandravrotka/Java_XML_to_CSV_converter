package com.xmlconverter.controller;

import com.xmlconverter.service.XmlProcessingService;
import com.xmlconverter.validator.XmlUploadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/xml")
class XmlUploadController {
    private static final Logger log = LoggerFactory.getLogger(XmlUploadController.class);

    @Autowired XmlProcessingService xmlProcessingService;
    @Autowired XmlUploadValidator xmlUploadValidator;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> uploadGamesXmlFile(@RequestParam("file") MultipartFile file) {
        try {
            String validationError = xmlUploadValidator.validate(file);
            if (validationError != null) {
                return ResponseHandler.createErrorResponse(validationError, BAD_REQUEST);
            }

            final byte[] csvBytes = xmlProcessingService.processXmlFile(file);

            return ResponseHandler.createSuccessResponse(csvBytes);

        } catch (Exception exception) {
            log.error("Ошибка при обработке загрузки XML-файла", exception);
            return ResponseHandler.createErrorResponse("Ошибка обработки: " + exception.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }
}