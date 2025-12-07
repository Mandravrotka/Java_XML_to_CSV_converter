package com.xmlconverter.controller;

import com.xmlconverter.model.Games;
import com.xmlconverter.service.GameService;
import com.xmlconverter.service.XmlParserService;
import com.xmlconverter.service.CsvGeneratorService;
import com.xmlconverter.validator.XmlUploadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/xml")
public class XmlUploadController {

    private static final Logger log = LoggerFactory.getLogger(XmlUploadController.class);

    @Autowired private GameService gameService;
    @Autowired private XmlParserService xmlParserService;
    @Autowired private CsvGeneratorService csvGeneratorService;
    @Autowired private XmlUploadValidator xmlUploadValidator;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> uploadGamesXmlFile(@RequestParam("file") MultipartFile file) {
        try {
            // Вся валидация
            String validationError = xmlUploadValidator.validate(file);
            if (validationError != null) {
                return errorResponse(validationError, 400);
            }

            // Если валидно — парсим и обрабатываем
            Games games = xmlParserService.parse(file);
            Games sortedGames = gameService.getSortedBySalesDesc(games);
            byte[] csvBytes = csvGeneratorService.generateCsv(sortedGames);

            return okResponse(csvBytes);

        } catch (Exception exception) {
            log.error("Ошибка при обработке загрузки XML-файла", exception);
            return errorResponse("Ошибка обработки: " + exception.getMessage(), 500);
        }
    }

    private ResponseEntity<byte[]> okResponse(byte[] csvBytes) {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=Example.csv")
                .header("Content-Type", "text/csv;charset=UTF-8")
                .body(csvBytes);
    }

    private ResponseEntity<byte[]> errorResponse(String message, int statusCode) {
        byte[] body = message.getBytes(UTF_8);
        if (statusCode == 400) {
            return ResponseEntity.badRequest()
                    .header("Content-Type", MediaType.TEXT_PLAIN_VALUE)
                    .body(body);
        } else {
            return ResponseEntity.status(statusCode)
                    .header("Content-Type", MediaType.TEXT_PLAIN_VALUE)
                    .body(body);
        }
    }
}