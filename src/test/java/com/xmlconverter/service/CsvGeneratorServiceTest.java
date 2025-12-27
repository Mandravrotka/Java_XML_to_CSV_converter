package com.xmlconverter.service;

import com.xmlconverter.model.Game;
import com.xmlconverter.model.Games;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static java.nio.charset.StandardCharsets.UTF_8;

class CsvGeneratorServiceTest {
    private CsvGeneratorService csvGeneratorService;

    @BeforeEach
    void setUp() {
        csvGeneratorService = new CsvGeneratorService();
    }

    private Game gameA() {
        return new Game("Игра A", "01-01-2020", "Экшен", 90, 5000000, "Dev1", "17+");
    }

    private Game gameB() {
        return new Game("Игра B", "15-05-2019", "РПГ", 95, 8000000, "Dev2", "18+");
    }

    private Games gamesWithTwoGames() {
        return new Games(List.of(gameA(), gameB()));
    }

    private Games emptyGames() {
        return new Games(List.of());
    }

    private List<String> generateCsvLines(Games games) {
        return new String(csvGeneratorService.generateCsv(games), UTF_8).lines().toList();
    }

    @Test
    @DisplayName("Должен сгенерировать CSV с корректным заголовком и данными")
    void shouldGenerateValidCsvWithHeaderAndData() {
        assertThat(generateCsvLines(gamesWithTwoGames())).hasSize(3)
            .startsWith("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг")
            .contains("Игра A,01-01-2020,Экшен,90,5000000,Dev1,17+")
            .contains("Игра B,15-05-2019,РПГ,95,8000000,Dev2,18+");
    }

    @Test
    @DisplayName("Должен вернуть CSV с только заголовком, если список игр пустой")
    void shouldGenerateCsvWithHeaderOnly_WhenNoGames() {
        assertThat(generateCsvLines(emptyGames())).hasSize(1)
            .startsWith("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг");
    }
}