package com.xmlconverter.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class XmlProcessingService {
    XmlParserService xmlParserService;
    GameService gameService;
    CsvGeneratorService csvGeneratorService;

    public byte[] processXmlFile(@NonNull final MultipartFile file) throws Exception {
        try {
            return csvGeneratorService.generateCsv(
                gameService.getSortedBySalesDesc(xmlParserService.parse(file)));
        } catch (Exception thrown) {
            throw new Exception("Ошибка обработки XML: ", thrown);
        }
    }
}