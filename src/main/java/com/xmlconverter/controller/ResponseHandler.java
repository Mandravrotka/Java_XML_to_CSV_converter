package com.xmlconverter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ContentDisposition;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.TEXT_PLAIN;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseHandler {
    private static final String CSV_FILENAME = "Example.csv";
    private static final MediaType CSV_CONTENT_TYPE = MediaType.parseMediaType("text/csv;charset=UTF-8");

    public static ResponseEntity<byte[]> createSuccessResponse(final byte[] csvBytes) {
        return ResponseEntity.ok()
            .header(CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename(CSV_FILENAME)
                .build().toString())
            .contentType(CSV_CONTENT_TYPE)
            .body(csvBytes);
    }

    public static ResponseEntity<byte[]> createErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .contentType(TEXT_PLAIN)
                .body(message.getBytes(UTF_8));
    }
}