package com.xmlconverter.service;

import com.xmlconverter.model.Game;
import com.xmlconverter.model.Games;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    @DisplayName("Должен отсортировать игры по продажам по убыванию")
    void shouldSortGamesBySalesDesc() {
        Game game1 = new Game("Игра A", "01-01-2020", "Экшен", 90, 5000000, "Dev1", "17+");
        Game game2 = new Game("Игра B", "01-01-2020", "РПГ", 85, 10000000, "Dev2", "17+");
        Game game3 = new Game("Игра C", "01-01-2020", "Платформер", 95, 2000000, "Dev3", "10+");

        Games inputGames = new Games(List.of(game1, game2, game3));
        Games result = gameService.getSortedBySalesDesc(inputGames);

        assertNotNull(result);
        assertNotNull(result.getGames());
        assertEquals(3, result.getGames().size());
        assertEquals("Игра B", result.getGames().get(0).getTitle()); // 10M
        assertEquals("Игра A", result.getGames().get(1).getTitle()); // 5M
        assertEquals("Игра C", result.getGames().get(2).getTitle()); // 2M
    }

    @Test
    @DisplayName("Должен вернуть пустой список, если входной список пустой")
    void shouldReturnEmptyList_WhenInputIsEmpty() {
        Games inputGames = new Games(List.of());
        Games result = gameService.getSortedBySalesDesc(inputGames);

        assertNotNull(result);
        assertNotNull(result.getGames());
        assertTrue(result.getGames().isEmpty());
    }

    @Test
    @DisplayName("Должен вернуть пустой список, если входной объект null")
    void shouldReturnEmptyList_WhenInputIsNull() {
        Games result = gameService.getSortedBySalesDesc(null);

        assertNotNull(result);
        assertNotNull(result.getGames());
        assertTrue(result.getGames().isEmpty());
    }

    @Test
    @DisplayName("Должен вернуть пустой список, если games.getGames() == null")
    void shouldReturnEmptyList_WhenGamesListIsNull() {
        Games gamesWithNullList = new Games(null);
        Games result = gameService.getSortedBySalesDesc(gamesWithNullList);

        assertNotNull(result);
        assertNotNull(result.getGames());
        assertTrue(result.getGames().isEmpty());
    }

    @Test
    @DisplayName("Должен корректно обрабатывать игры с одинаковым количеством продаж")
    void shouldHandleGamesWithSameSales() {
        Game game1 = new Game("Игра A", "01-01-2020", "Экшен", 90, 5000000, "Dev1", "17+");
        Game game2 = new Game("Игра B", "01-01-2020", "РПГ", 85, 5000000, "Dev2", "17+");

        Games inputGames = new Games(List.of(game1, game2));
        Games result = gameService.getSortedBySalesDesc(inputGames);

        assertNotNull(result);
        assertEquals(2, result.getGames().size());
        // Обе имеют одинаковые продажи — порядок может быть любым, но обе должны быть
        assertTrue(result.getGames().contains(game1));
        assertTrue(result.getGames().contains(game2));
    }
}