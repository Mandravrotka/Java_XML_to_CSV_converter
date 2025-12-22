package com.xmlconverter.service;

import com.xmlconverter.model.Game;
import com.xmlconverter.model.Games;
import lombok.val;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvGeneratorServiceTest {

    private CsvGeneratorService csvGeneratorService;

    @BeforeEach
    void setUp() {
        csvGeneratorService = new CsvGeneratorService();
    }

    @Test
    @DisplayName("Должен сгенерировать CSV с корректным заголовком и данными")
    void shouldGenerateValidCsvWithHeaderAndData() throws Exception {
        val game1 = new Game("Игра A", "01-01-2020", "Экшен", 90, 5000000, "Dev1", "17+");
        val game2 = new Game("Игра B", "15-05-2019", "РПГ", 95, 8000000, "Dev2", "18+");
        val games = new Games(List.of(game1, game2));

        val csvBytes = csvGeneratorService.generateCsv(games);
        val csvContent = new String(csvBytes, StandardCharsets.UTF_8);

        assertNotNull(csvContent);
        assertTrue(csvContent.startsWith("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг"));
        assertEquals(3, csvContent.split("\n").length, "Ожидаются заголовок + 2 строки данных");

        val lines = csvContent.split("\n");
        assertTrue(lines[1].startsWith("Игра A,01-01-2020,Экшен,90,5000000,Dev1,17+"));
        assertTrue(lines[2].startsWith("Игра B,15-05-2019,РПГ,95,8000000,Dev2,18+"));
    }

    @Test
    @DisplayName("Должен вернуть CSV с только заголовком, если список игр пустой")
    void shouldGenerateCsvWithHeaderOnly_WhenNoGames() throws Exception {
        val emptyGames = new Games(List.of());
        val csvBytes = csvGeneratorService.generateCsv(emptyGames);
        val csvContent = new String(csvBytes, StandardCharsets.UTF_8);

        assertNotNull(csvContent);
        assertTrue(csvContent.contains("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг"));
    }

    @Test
    @DisplayName("Должен корректно обрабатывать null список игр")
    void shouldHandleNullGamesList() throws Exception {
        val gamesWithNullList = new Games(null);
        val csvBytes = csvGeneratorService.generateCsv(gamesWithNullList);
        val csvContent = new String(csvBytes, StandardCharsets.UTF_8);

        assertNotNull(csvContent);
        assertTrue(csvContent.contains("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг"));
    }


    @Test
    @DisplayName("Должен генерировать валидный CSV, который можно распарсить")
    void shouldGenerateParseableCsv() throws Exception {
        val game = new Game("Игра", "01-01-2020", "Жанр", 80, 1000000, "Dev", "12+");
        val games = new Games(List.of(game));

        val csvBytes = csvGeneratorService.generateCsv(games);
        val bis = new ByteArrayInputStream(csvBytes);
        val reader = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));

        val header = reader.readLine();
        val dataLine = reader.readLine();

        assertEquals("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг", header);
        assertTrue(dataLine.startsWith("Игра,01-01-2020,Жанр,80,1000000,Dev,12+"));
        assertNull(reader.readLine(), "CSV должен содержать только заголовок и одну строку данных");
    }
}