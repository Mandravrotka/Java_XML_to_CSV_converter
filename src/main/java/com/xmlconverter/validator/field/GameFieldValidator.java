package com.xmlconverter.validator.field;

import com.xmlconverter.model.Game;

@FunctionalInterface
public interface GameFieldValidator {
    String validate(Game game);
}