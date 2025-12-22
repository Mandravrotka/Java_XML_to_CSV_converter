package com.xmlconverter.validator;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UploadValidatorTest {

    @Autowired
    private UploadValidator uploadValidator;

    @Test
    @DisplayName("Должен вернуть ошибку, если файл пустой")
    void shouldReturnError_WhenFileIsEmpty() {
        val emptyFile = new MockMultipartFile(
                "file", "example.xml", "application/xml", new byte[0]
        );

        val error = uploadValidator.validateFile(emptyFile);

        assertNotNull(error);
        assertEquals("Файл пустой", error);
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если имя файла null")
    void shouldReturnError_WhenFilenameIsNull() throws Exception {
        val fileWithNullName = new MockMultipartFile(
                "file", null, "application/xml", "<игры/>".getBytes()
        );

        val error = uploadValidator.validateFile(fileWithNullName);

        assertNotNull(error);
        assertEquals("Имя файла не указано", error);
    }

}