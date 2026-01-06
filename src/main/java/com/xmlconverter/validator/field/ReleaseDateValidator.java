package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ReleaseDateValidator implements GameFieldValidator {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public String validate(@NonNull final Game game) {
        val releaseDate = game.getReleaseDate();
        if (isBlank(releaseDate)) {
            return "Дата релиза не должна быть пустой";
        }

        try {
            val year = LocalDate.parse(releaseDate, FORMATTER).getYear();
            if (year < 1950 || year > 2026) {
                return "Год должен быть от 1950 до 2026";
            }
        } catch (DateTimeParseException thrown) {
            return "Указана некорректная дата";
        }

        return null;
    }
}