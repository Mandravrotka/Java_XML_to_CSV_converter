package com.xmlconverter.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
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
            val games = xmlParserService.parse(file);
            val sortedGames = gameService.getSortedBySalesDesc(games);

            return csvGeneratorService.generateCsv(sortedGames);
        } catch (Exception exception) {
            throw new Exception("Ошибка обработки XML: ", exception);
        }
    }
}