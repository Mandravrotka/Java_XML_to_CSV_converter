package com.xmlconverter.validator;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class XmlUploadValidatorTest {
    @Autowired
    private XmlUploadValidator xmlUploadValidator;

    private MultipartFile loadValidXml() throws Exception {
        return new MockMultipartFile(
            "file",
            "Пример.xml",
            "application/xml",
            Files.readAllBytes(new ClassPathResource("Пример.xml").getFile().toPath())
        );
    }

    @Test
    @DisplayName("Должен вернуть null, если всё валидно")
    void shouldReturnNull_WhenAllValid() throws Exception {
        assertThat(xmlUploadValidator.validate(loadValidXml())).isNull();
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если файл пустой")
    void shouldReturnError_WhenFileIsEmpty() {
        assertThat(xmlUploadValidator.validate(new MockMultipartFile(
            "file",
            "empty.xml",
            "application/xml",
            new byte[0])))
            .isEqualTo("Файл пустой");
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если файл не XML")
    void shouldReturnError_WhenNotXml() {
        assertThat(xmlUploadValidator.validate(new MockMultipartFile(
            "file",
            "data.txt",
            "text/plain",
            "content".getBytes())))
            .isEqualTo("Требуется XML-файл");
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если в XML нет игр")
    void shouldReturnError_WhenNoGames() {
        assertThat(xmlUploadValidator.validate(new MockMultipartFile(
            "file",
            "no-games.xml",
            "application/xml",
            "<игры></игры>".getBytes())))
            .isEqualTo("Файл не содержит игр");
    }
}