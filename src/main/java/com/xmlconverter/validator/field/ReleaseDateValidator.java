package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class ReleaseDateValidator implements GameFieldValidator {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    final Clock clock = Clock.systemDefaultZone();

    @Override
    public String validate(@NonNull final Game game) {
        val releaseDate = game.getReleaseDate();
        if (isBlank(releaseDate)) {
            return "Дата релиза не должна быть пустой";
        }

        try {
            val year = LocalDate.parse(releaseDate, formatter).getYear();
            val currentYear = LocalDate.now(clock).getYear();
            if (year < 1950 || year > currentYear) {
                return "Год должен быть от 1950 до " + currentYear;
            }
        } catch (DateTimeParseException thrown) {
            return "Указана некорректная дата";
        }

        return null;
    }
}