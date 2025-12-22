package com.xmlconverter.service;

import com.xmlconverter.model.Games;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XmlParserServiceTest {

    @Autowired
    private XmlParserService xmlParserService;

    @Test
    @DisplayName("Должен успешно распарсить корректный XML с играми")
    void shouldParseValidXml() throws Exception {
        // Подготавливаем файл из ресурсов
        val xmlFile = new ClassPathResource("Пример.xml").getFile();
        val content = Files.readAllBytes(xmlFile.toPath());
        val multipartFile = new MockMultipartFile(
                "file",
                "Пример.xml",
                "application/xml",
                content
        );

        // Выполняем парсинг
        val games = xmlParserService.parse(multipartFile);

        // Проверяем результат
        assertNotNull(games, "Результат парсинга не должен быть null");
        assertNotNull(games.getGames(), "Список игр не должен быть null");
        assertFalse(games.getGames().isEmpty(), "Список игр не должен быть пустым");
        assertEquals(10, games.getGames().size(), "Ожидалось 10 игр в файле");
        assertEquals("Зе Легенд оф Зельда: Бреф оф зе Вайлд", games.getGames().get(0).getTitle());
        assertEquals("Зе Витчер 3: Вайлд Хант", games.getGames().get(9).getTitle());
    }

    @Test
    @DisplayName("Должен выбросить исключение при невалидном XML")
    void shouldThrowException_WhenXmlIsMalformed() throws Exception {
        // Несогласованные теги — ошибка парсинга
        val invalidXml = "<игры><игра><название>Test</игры>";
        val file = new MockMultipartFile(
                "file",
                "invalid.xml",
                "application/xml",
                invalidXml.getBytes()
        );

        // Ожидаем RuntimeException из-за JAXBException
        val exception = assertThrows(RuntimeException.class, () -> {
            xmlParserService.parse(file);
        });

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Ошибка парсинга XML"));
    }

    @Test
    @DisplayName("Должен выбросить исключение при пустом XML")
    void shouldThrowException_WhenXmlIsEmpty() throws Exception {
        val emptyFile = new MockMultipartFile(
                "file",
                "empty.xml",
                "application/xml",
                new byte[0]
        );

        val exception = assertThrows(RuntimeException.class, () -> {
            xmlParserService.parse(emptyFile);
        });

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Ошибка парсинга XML"));
    }

    @Test
    @DisplayName("Должен обработать XML без игр (пустой список)")
    void shouldParseXmlWithNoGames() throws Exception {
        val xmlContent = "<игры></игры>";
        val file = new MockMultipartFile(
                "file",
                "no-games.xml",
                "application/xml",
                xmlContent.getBytes()
        );

        val games = xmlParserService.parse(file);

        assertNotNull(games);
        assertNull(games.getGames(), "Список игр должен быть пустым");
    }
}