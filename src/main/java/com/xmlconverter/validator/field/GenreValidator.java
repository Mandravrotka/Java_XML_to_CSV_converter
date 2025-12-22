package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class GenreValidator implements GameFieldValidator {
    @Override
    public String validate(@NonNull final Game game) {
        if (isBlank(game.getGenre())) {
            return "Жанр не должен быть пустым";
        }
        return null;
    }
}