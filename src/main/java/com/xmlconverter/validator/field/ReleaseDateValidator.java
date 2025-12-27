package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Component
public class ReleaseDateValidator implements GameFieldValidator {
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public String validate(@NonNull final Game game) {
        val releaseDate = game.getReleaseDate();
        if (releaseDate == null || releaseDate.trim().isEmpty()) {
            return "Дата релиза не должна быть пустой";
        }

        if (!DATE_PATTERN.matcher(releaseDate).matches()) {
            return "Дата должна быть в формате DD-MM-YYYY";
        }

        try {
            val year = LocalDate.parse(releaseDate, FORMATTER).getYear();
            if (year < 1950 || year > 2025) {
                return "Год должен быть от 1950 до 2025";
            }
        } catch (DateTimeParseException e) {
            return "Указана некорректная дата";
        }

        return null;
    }
}