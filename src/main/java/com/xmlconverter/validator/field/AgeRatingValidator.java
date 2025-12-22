package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class AgeRatingValidator implements GameFieldValidator {
    private static final Pattern AGE_RATING_PATTERN = Pattern.compile("^(?:[0-9]|1[0-9]|2[01])\\+$");

    @Override
    public String validate(@NonNull final Game game) {
        val ageRating = game.getAgeRating();
        if (ageRating == null || ageRating.trim().isEmpty()) {
            return "Возрастной рейтинг не должен быть пустым";
        }
        if (!AGE_RATING_PATTERN.matcher(ageRating).matches()) {
            return "Возрастной рейтинг должен быть в формате N+, где N от 0 до 21";
        }
        return null;
    }
}