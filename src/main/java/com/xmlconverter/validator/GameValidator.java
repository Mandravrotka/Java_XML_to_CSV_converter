package com.xmlconverter.validator;

import com.xmlconverter.validator.field.GameFieldValidator;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.xmlconverter.model.Game;

import java.util.List;
import java.util.Objects;

@Component
@FieldDefaults(makeFinal = true)
public class GameValidator {
    final List<GameFieldValidator> validators;

    @Autowired
    public GameValidator(List<GameFieldValidator> validators) {
        this.validators = List.copyOf(validators);
    }

    public String validate(@NonNull final Game game) {
        return validators.stream()
            .map(validator -> validator.validate(game))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}