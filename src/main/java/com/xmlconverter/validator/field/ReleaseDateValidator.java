package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

@Component
public class ReleaseDateValidator implements GameFieldValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public String validate(@NonNull final Game game) {
        String date = game.getReleaseDate();
        if (StringUtils.isBlank(date)) {
            return "Дата не должна быть пустой";
        }
        if (date.length() != 10 || date.charAt(2) != '-' || date.charAt(5) != '-') {
            return "Дата должна быть в формате DD-MM-YYYY";
        }
        try {
            LocalDate parsed = LocalDate.parse(date, DATE_FORMATTER);
            int year = parsed.getYear();
            int current = Year.now().getValue();
            if (year < 1950 || year > current) {
                return "Год должен быть от 1950 до " + current;
            }
        } catch (Exception e) {
            return "Некорректная дата";
        }
        return null;
    }
}