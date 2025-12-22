package com.xmlconverter.validator;

import com.xmlconverter.validator.field.GameFieldValidator;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.xmlconverter.model.Game;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

@Component
@FieldDefaults(makeFinal = true)
public class GameValidator {
    final List<GameFieldValidator> validators;

    @Autowired
    public GameValidator(List<GameFieldValidator> validators) {
        this.validators = List.copyOf(validators);
    }

    public String validate(@NonNull final Game game) {
        val errors = validators.stream()
            .map(validator -> validator.validate(game))
            .filter(Objects::nonNull)
            .toList();
        
        return errors.isEmpty() ? null : String.join("; ", errors);
    }
}