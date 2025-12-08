package com.xmlconverter.service;

import com.xmlconverter.model.Game;
import com.xmlconverter.model.Games;
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
        Game game1 = new Game("Игра A", "01-01-2020", "Экшен", 90, 5000000, "Dev1", "17+");
        Game game2 = new Game("Игра B", "15-05-2019", "РПГ", 95, 8000000, "Dev2", "18+");
        Games games = new Games(List.of(game1, game2));

        byte[] csvBytes = csvGeneratorService.generateCsv(games);
        String csvContent = new String(csvBytes, StandardCharsets.UTF_8);

        assertNotNull(csvContent);
        assertTrue(csvContent.startsWith("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг"));
        assertEquals(3, csvContent.split("\n").length, "Ожидаются заголовок + 2 строки данных");

        String[] lines = csvContent.split("\n");
        assertEquals("Игра A,01-01-2020,Экшен,90,5000000,Dev1,17+\r", lines[1]);
        assertEquals("Игра B,15-05-2019,РПГ,95,8000000,Dev2,18+\r", lines[2]);
    }

    @Test
    @DisplayName("Должен вернуть CSV с только заголовком, если список игр пустой")
    void shouldGenerateCsvWithHeaderOnly_WhenNoGames() throws Exception {
        Games emptyGames = new Games(List.of());
        byte[] csvBytes = csvGeneratorService.generateCsv(emptyGames);
        String csvContent = new String(csvBytes, StandardCharsets.UTF_8);

        assertNotNull(csvContent);
        assertEquals("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг\r\n", csvContent);
    }

    @Test
    @DisplayName("Должен корректно обрабатывать null список игр")
    void shouldHandleNullGamesList() throws Exception {
        Games gamesWithNullList = new Games(null);
        byte[] csvBytes = csvGeneratorService.generateCsv(gamesWithNullList);
        String csvContent = new String(csvBytes, StandardCharsets.UTF_8);

        assertNotNull(csvContent);
        assertEquals("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг\r\n", csvContent);
    }


    @Test
    @DisplayName("Должен генерировать валидный CSV, который можно распарсить")
    void shouldGenerateParseableCsv() throws Exception {
        Game game = new Game("Игра", "01-01-2020", "Жанр", 80, 1000000, "Dev", "12+");
        Games games = new Games(List.of(game));

        byte[] csvBytes = csvGeneratorService.generateCsv(games);
        ByteArrayInputStream bis = new ByteArrayInputStream(csvBytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));

        String header = reader.readLine();
        String dataLine = reader.readLine();

        assertEquals("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг", header);
        assertEquals("Игра,01-01-2020,Жанр,80,1000000,Dev,12+", dataLine);
        assertNull(reader.readLine(), "CSV должен содержать только заголовок и одну строку данных");
    }
}