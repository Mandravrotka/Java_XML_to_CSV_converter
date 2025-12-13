package com.xmlconverter.validator;

import com.xmlconverter.model.Games;
import com.xmlconverter.service.XmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class XmlUploadValidator {
    @Autowired UploadValidator uploadValidator;
    @Autowired GameValidator gameValidator;
    @Autowired XmlParserService xmlParserService;

    public String validate(@NonNull final MultipartFile file) {
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

        return games.getGames().stream()
            .map(game -> gameValidator.validate(game))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}