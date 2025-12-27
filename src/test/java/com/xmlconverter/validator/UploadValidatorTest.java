package com.xmlconverter.validator;

import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

class UploadValidatorTest {
    private UploadValidator uploadValidator;

    @BeforeEach
    void setUp() {
        uploadValidator = new UploadValidator();
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если файл пустой")
    void shouldReturnError_WhenFileIsEmpty() {
        assertThat(uploadValidator.validateFile(new MockMultipartFile(
            "file",
            "example.xml",
            "application/xml",
            new byte[0])))
            .isEqualTo("Файл пустой");
    }

    @Test
    @DisplayName("Должен вернуть ошибку, если имя файла null")
    void shouldReturnError_WhenFilenameIsNull() {
        assertThat(uploadValidator.validateFile(new MockMultipartFile(
            "file",
            null,
            "application/xml",
            "<игры/>".getBytes())))
            .isEqualTo("Имя файла не указано");
    }

}