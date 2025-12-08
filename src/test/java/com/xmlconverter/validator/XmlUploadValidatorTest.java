package com.xmlconverter.validator;

import com.xmlconverter.model.Games;
import com.xmlconverter.service.XmlParserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XmlUploadValidatorTest {

    @Autowired
    private XmlUploadValidator xmlUploadValidator;

    private MultipartFile validXmlFile;

    @BeforeEach
    void setUp() throws Exception {
        File xmlFile = new ClassPathResource("Пример.xml").getFile();
        byte[] content = Files.readAllBytes(xmlFile.toPath());
        validXmlFile = new MockMultipartFile("file", "Пример.xml", "application/xml", content);
    }

    @Test
    @DisplayName("Должен вернуть null, если всё валидно")
    void shouldReturnNull_WhenAllValid() throws Exception {
        String error = xmlUploadValidator.validate(validXmlFile);
        assertNull(error);
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если файл пустой")
    void shouldReturnError_WhenFileIsEmpty() {
        MultipartFile emptyFile = new MockMultipartFile("file", "empty.xml", "application/xml", new byte[0]);
        String error = xmlUploadValidator.validate(emptyFile);
        assertEquals("Файл пустой", error);
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если файл не XML")
    void shouldReturnError_WhenNotXml() {
        MultipartFile txtFile = new MockMultipartFile("file", "data.txt", "text/plain", "content".getBytes());
        String error = xmlUploadValidator.validate(txtFile);
        assertEquals("Требуется XML-файл", error);
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если в XML нет игр")
    void shouldReturnError_WhenNoGames() throws Exception {
        String xmlContent = "<игры></игры>";
        MultipartFile noGamesFile = new MockMultipartFile("file", "no-games.xml", "application/xml", xmlContent.getBytes());
        String error = xmlUploadValidator.validate(noGamesFile);
        assertEquals("Файл не содержит игр", error);
    }
}