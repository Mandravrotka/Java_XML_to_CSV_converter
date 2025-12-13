package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class GenreValidator implements GameFieldValidator {
    @Override
    public String validate(@NonNull final Game game) {
        if (StringUtils.isBlank(game.getGenre())) {
            return "Жанр не должен быть пустым";
        }
        return null;
    }
}