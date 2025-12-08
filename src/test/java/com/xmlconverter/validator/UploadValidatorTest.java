package com.xmlconverter.validator;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UploadValidatorTest {

    @Autowired
    private UploadValidator uploadValidator;

    @Test
    @DisplayName("Должен вернуть ошибку, если файл пустой")
    void shouldReturnError_WhenFileIsEmpty() {
        MultipartFile emptyFile = new MockMultipartFile(
                "file", "example.xml", "application/xml", new byte[0]
        );

        String error = uploadValidator.validateFile(emptyFile);

        assertNotNull(error);
        assertEquals("Файл пустой", error);
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если имя файла null")
    void shouldReturnError_WhenFilenameIsNull() throws Exception {
        MultipartFile fileWithNullName = new MockMultipartFile(
                "file", null, "application/xml", "<игры/>".getBytes()
        );

        String error = uploadValidator.validateFile(fileWithNullName);

        assertNotNull(error);
        assertEquals("Имя файла не указано", error);
    }

}