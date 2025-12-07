package com.xmlconverter.service;

import com.xmlconverter.model.Games;
import com.xmlconverter.model.Game;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {
    public Games getSortedBySalesDesc(Games games) {
        if (games == null || games.getGames() == null || games.getGames().isEmpty()) {
            return new Games(List.of());
        }

        List<Game> sortedList = games.getGames().stream()
                .sorted(Comparator.comparingLong(Game::getSales).reversed())
                .collect(Collectors.toList());

        return new Games(sortedList);
    }
}