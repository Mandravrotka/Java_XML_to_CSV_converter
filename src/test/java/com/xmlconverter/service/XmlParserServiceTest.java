package com.xmlconverter.service;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlParserServiceTest {
    private XmlParserService xmlParserService;

    @BeforeEach
    void setUp() {
        xmlParserService = new XmlParserService();
    }

    private String messageExpectException(String name, String content) {
        return assertThrows(
            RuntimeException.class, () -> xmlParserService.parse(xmlFile(name, content))).getMessage();
    }

    private MultipartFile loadValidXml() throws Exception {
        return new MockMultipartFile(
            "file",
            "Пример.xml",
            "application/xml",
            Files.readAllBytes(new ClassPathResource("Пример.xml").getFile().toPath())
        );
    }

    private MultipartFile xmlFile(String name, String content) {
        return new MockMultipartFile(
            "file",
            name,
            "application/xml",
            content.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String malformedXml() {
        return "<игры><игра><название>Test</игры>";
    }

    private String noGamesXml() {
        return "<игры></игры>";
    }

    @Test
    @DisplayName("Должен успешно распарсить корректный XML с играми")
    void shouldParseValidXml() throws Exception {
        assertThat(xmlParserService.parse(loadValidXml()).getGames()).hasSize(10)
            .extracting("title")
            .startsWith("Зе Легенд оф Зельда: Бреф оф зе Вайлд")
            .endsWith("Зе Витчер 3: Вайлд Хант");
    }

    @Test
    @DisplayName("Должен выбросить исключение при невалидном XML")
    void shouldThrowException_WhenXmlIsMalformed() {
        assertThat(messageExpectException("invalid.xml", malformedXml()))
            .contains("Ошибка парсинга XML");
    }

    @Test
    @DisplayName("Должен выбросить исключение при пустом XML")
    void shouldThrowException_WhenXmlIsEmpty() {
        assertThat(messageExpectException("empty.xml", ""))
            .contains("Ошибка парсинга XML");
    }

    @Test
    @DisplayName("Должен обработать XML без игр (пустой список)")
    void shouldParseXmlWithNoGames() throws Exception {
        val games = xmlParserService.parse(xmlFile("no-games.xml", noGamesXml()));

        assertThat(games).isNotNull();
        assertThat(games.getGames()).isNull();
    }
}