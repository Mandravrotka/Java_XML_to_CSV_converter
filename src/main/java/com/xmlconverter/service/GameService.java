package com.xmlconverter.service;

import com.xmlconverter.model.Games;
import com.xmlconverter.model.Game;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    public Games getSortedBySalesDesc(@NonNull final Games games) {
        return Optional.ofNullable(games.getGames())
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .sorted(Comparator.comparingLong(Game::getSales).reversed())
                        .toList())
                .map(Games::new)
                .orElseGet(() -> new Games(List.of()));
    }
}