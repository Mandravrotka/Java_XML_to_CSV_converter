package com.xmlconverter.validator;

import com.xmlconverter.model.Games;
import com.xmlconverter.service.XmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class XmlUploadValidator {

    @Autowired private UploadValidator uploadValidator;
    @Autowired private GameValidator gameValidator;
    @Autowired private XmlParserService xmlParserService;

    public String validate(MultipartFile file) {
        // Валидация файла (пустой, расширение)
        String fileError = uploadValidator.validateFile(file);
        if (fileError != null) {
            return fileError;
        }
        String filename = file.getOriginalFilename();
        if (!filename.toLowerCase().endsWith(".xml")) {
            return "Требуется XML-файл";
        }

        // Парсинг и проверка, что есть игры
        Games games;
        try {
            games = xmlParserService.parse(file);
        } catch (Exception exception) {
            return "Не удалось распарсить XML: " + exception.getMessage();
        }

        if (games.getGames() == null || games.getGames().isEmpty()) {
            return "Файл не содержит игр";
        }

        // Валидация каждой игры
        for (var game : games.getGames()) {
            String gameError = gameValidator.validate(game);
            if (gameError != null) {
                return gameError;
            }
        }

        return null; // всё валидно
    }
}