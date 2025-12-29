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
        return Game.builder()
            .title("Игра A")
            .releaseDate("01-01-2020")
            .genre("Экшен")
            .rating(90)
            .sales(5000000)
            .developer("Dev1")
            .ageRating("17+")
            .build();
    }

    private Game gameB() {
        return Game.builder()
            .title("Игра B")
            .releaseDate("01-01-2020")
            .genre("РПГ")
            .rating(85)
            .sales(10000000)
            .developer("Dev2")
            .ageRating("17+")
            .build();
    }

    private Game gameC() {
        return Game.builder()
            .title("Игра C")
            .releaseDate("01-01-2020")
            .genre("Платформер")
            .rating(95)
            .sales(2000000)
            .developer("Dev3")
            .ageRating("10+")
            .build();
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