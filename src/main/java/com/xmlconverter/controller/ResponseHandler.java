package com.xmlconverter.controller;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ContentDisposition;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.TEXT_PLAIN;

public class ResponseHandler {
    public static ResponseEntity<byte[]> createSuccessResponse(byte[] csvBytes) {
        val contentDisposition = ContentDisposition.attachment()
                .filename("Example.csv")
                .build();

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, contentDisposition.toString())
                .contentType(MediaType.parseMediaType("text/csv;charset=UTF-8"))
                .body(csvBytes);
    }

    public static ResponseEntity<byte[]> createErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .contentType(TEXT_PLAIN)
                .body(message.getBytes(UTF_8));
    }
}