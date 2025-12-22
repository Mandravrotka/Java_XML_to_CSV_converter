package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SalesValidator implements GameFieldValidator {
    @Override
    public String validate(@NonNull final Game game) {
        if (game.getSales() < 0) {
            return "Количество продаж должно быть неотрицательным числом";
        }
        return null;
    }
}