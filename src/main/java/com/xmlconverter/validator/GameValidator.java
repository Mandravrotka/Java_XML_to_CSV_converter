package com.xmlconverter.validator;

import com.xmlconverter.model.Game;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

@Component
public class GameValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public String validate(Game game) {
        if (game == null) {
            return "Игра не может быть null";
        }

        return firstError(
                () -> validateTitle(game.getTitle()),
                () -> validateReleaseDate(game.getReleaseDate()),
                () -> validateGenre(game.getGenre()),
                () -> validateRating(game.getRating()),
                () -> validateSales(game.getSales()),
                () -> validateDeveloper(game.getDeveloper()),
                () -> validateAgeRating(game.getAgeRating())
        );
    }

    @SafeVarargs
    private String firstError(Supplier<String>... suppliers) {
        for (Supplier<String> supplier : suppliers) {
            String error = supplier.get();
            if (error != null) {
                return error;
            }
        }
        return null;
    }

    private String validateTitle(String title) {
        if (StringUtils.isBlank(title)) {
            return "Название игры не должно быть пустым";
        }
        return null;
    }

    private String validateReleaseDate(String date) {
        if (StringUtils.isBlank(date)) {
            return "Дата не должна быть пустой";
        }
        if (!isValidDate(date)) {
            return "Дата должна быть в формате DD-MM-YYYY и существовать";
        }
        return null;
    }

    private String validateGenre(String genre) {
        if (StringUtils.isBlank(genre)) {
            return "Жанр не должен быть пустым";
        }
        return null;
    }

    private String validateRating(int rating) {
        if (rating < 0 || rating > 100) {
            return "Рейтинг должен быть от 0 до 100";
        }
        return null;
    }

    private String validateSales(long sales) {
        if (sales < 0) {
            return "Количество продаж должно быть неотрицательным числом";
        }
        return null;
    }

    private String validateDeveloper(String developer) {
        if (StringUtils.isBlank(developer)) {
            return "Разработчик не должен быть пустым";
        }
        return null;
    }

    private String validateAgeRating(String ageRating) {
        if (StringUtils.isBlank(ageRating)) {
            return "Возрастной рейтинг не должен быть пустым";
        }
        if (!isValidAgeRating(ageRating)) {
            return "Возрастной рейтинг должен быть в формате N+, где N от 0 до 21";
        }
        return null;
    }

    private boolean isValidDate(String date) {
        if (date.length() != 10) return false;
        if (date.charAt(2) != '-' || date.charAt(5) != '-') return false;
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            int year = parsedDate.getYear();
            int currentYear = Year.now().getValue();
            return year >= 1950 && year <= currentYear;
        } catch (DateTimeException exception) {
            return false;
        }
    }

    private boolean isValidAgeRating(String rating) {
        if (!rating.endsWith("+")) return false;
        String numPart = rating.substring(0, rating.length() - 1);
        if (StringUtils.isBlank(numPart)) return false;
        if (!StringUtils.isNumeric(numPart)) return false;
        int n = Integer.parseInt(numPart);
        return n >= 0 && n <= 21;
    }
}