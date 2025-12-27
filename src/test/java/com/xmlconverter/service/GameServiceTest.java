package com.xmlconverter.service;

import com.xmlconverter.model.Game;
import com.xmlconverter.model.Games;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GameServiceTest {
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    private Game gameA() {
        return new Game("Игра A", "01-01-2020", "Экшен", 90, 5000000, "Dev1", "17+");
    }

    private Game gameB() {
        return new Game("Игра B", "01-01-2020", "РПГ", 85, 10000000, "Dev2", "17+");
    }

    private Game gameC() {
        return new Game("Игра C", "01-01-2020", "Платформер", 95, 2000000, "Dev3", "10+");
    }

    private Games gamesWithSameSales() {
        return new Games(List.of(gameA().setSales(100), gameB().setSales(100)));
    }

    @Test
    @DisplayName("Должен отсортировать игры по продажам по убыванию")
    void shouldSortGamesBySalesDesc() {
        assertThat(gameService.getSortedBySalesDesc(new Games(List.of(gameA(), gameB(), gameC()))))
            .usingRecursiveComparison()
            .isEqualTo(new Games(List.of(gameB(), gameA(), gameC())));
    }

    @Test
    @DisplayName("Должен вернуть пустой список, если входной список пустой")
    void shouldReturnEmptyList_WhenInputIsEmpty() {
        assertThat(gameService.getSortedBySalesDesc(new Games(List.of())))
            .usingRecursiveComparison()
            .isEqualTo(new Games(List.of()));
    }

    @Test
    @DisplayName("Должен корректно обрабатывать игры с одинаковым количеством продаж")
    void shouldHandleGamesWithSameSales() {
        assertThat(gameService.getSortedBySalesDesc(gamesWithSameSales()))
            .usingRecursiveComparison()
            .isEqualTo(gamesWithSameSales());
    }
}