package com.xmlconverter.service;

import com.xmlconverter.model.Games;
import com.xmlconverter.model.Game;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;

@Service
public class GameService {

    public Games getSortedBySalesDesc(Games games) {
        if (games == null || games.getGames() == null || games.getGames().isEmpty()) {
            return new Games();
        }

        Games sorted = new Games();
        sorted.setGames(new ArrayList<>(games.getGames()));
        sorted.getGames().sort(Comparator.comparingLong(Game::getSales).reversed());
        return sorted;
    }
}