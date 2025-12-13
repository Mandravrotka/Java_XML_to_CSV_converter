package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RatingValidator implements GameFieldValidator {
    @Override
    public String validate(@NonNull final Game game) {
        int rating = game.getRating();
        if (rating < 0 || rating > 100) {
            return "Рейтинг должен быть от 0 до 100";
        }
        return null;
    }
}