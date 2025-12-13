package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AgeRatingValidator implements GameFieldValidator {
    @Override
    public String validate(@NonNull final Game game) {
        String ageRating = game.getAgeRating();
        if (StringUtils.isBlank(ageRating)) {
            return "Возрастной рейтинг не должен быть пустым";
        }
        if (!isValidAgeRating(ageRating)) {
            return "Возрастной рейтинг должен быть в формате N+, где N от 0 до 21";
        }
        return null;
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