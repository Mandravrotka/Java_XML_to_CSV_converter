package com.xmlconverter.validator;

import com.xmlconverter.model.Games;
import com.xmlconverter.service.XmlParserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class XmlUploadValidator {
    UploadValidator uploadValidator;
    GameValidator gameValidator;
    XmlParserService xmlParserService;

    public String validate(@NonNull final MultipartFile file) {
        // Валидация файла (пустой, расширение)
        val fileError = uploadValidator.validateFile(file);
        if (fileError != null) {
            return fileError;
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".xml")) {
            return "Требуется XML-файл";
        }

        // Парсинг и проверка, что есть игры
        Games games;
        try {
            games = xmlParserService.parse(file);
        } catch (Exception exception) {
            return "Не удалось распарсить XML: " + exception.getMessage();
        }

        if (games.getGames().isEmpty()) {
            return "Файл не содержит игр";
        }

        val errors = games.getGames().stream()
            .filter(Objects::nonNull)
            .map(gameValidator::validate)
            .filter(Objects::nonNull)
            .toList();
        
        return errors.isEmpty() ? null : String.join("; ", errors);
    }
}