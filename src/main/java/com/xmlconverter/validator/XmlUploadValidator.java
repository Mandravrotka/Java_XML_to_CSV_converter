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
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class XmlUploadValidator {
    UploadValidator uploadValidator;
    GameValidator gameValidator;
    XmlParserService xmlParserService;

    public String validate(@NonNull final MultipartFile file) {
        val fileError = uploadValidator.validateFile(file);
        if (fileError != null) {
            return fileError;
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".xml")) {
            return "Требуется XML-файл";
        }

        Games games;
        try {
            games = xmlParserService.parse(file);
        } catch (Exception thrown) {
            return "Не удалось распарсить XML: " + thrown.getMessage();
        }

        // JAXB игнорирует @NonNull
        if (games.getGames() == null) {
            return "Данные игр не могут быть обработаны";
        }

        return Optional.of(games.getGames().stream()
                .filter(Objects::nonNull)
                .map(gameValidator::validate)
                .filter(Objects::nonNull)
                .toList())
            .filter(list -> !list.isEmpty())
            .map(list -> String.join("; ", list))
            .orElse(null);
    }
}