package com.xmlconverter.service;

import com.xmlconverter.model.Games;
import com.xmlconverter.model.Game;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.of;
import static java.util.Comparator.comparingLong;

@Service
public class GameService {
    public Games getSortedBySalesDesc(@NonNull final Games games) {
        return of(games.getGames())
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .sorted(comparingLong(Game::getSales).reversed())
                        .toList())
                .map(Games::new)
                .orElseGet(() -> new Games(List.of()));
    }
}