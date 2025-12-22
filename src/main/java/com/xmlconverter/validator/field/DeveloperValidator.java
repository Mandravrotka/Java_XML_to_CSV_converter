package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class DeveloperValidator implements GameFieldValidator {
    @Override
    public String validate(@NonNull final Game game) {
        if (isBlank(game.getDeveloper())) {
            return "Разработчик не должен быть пустым";
        }
        return null;
    }
}