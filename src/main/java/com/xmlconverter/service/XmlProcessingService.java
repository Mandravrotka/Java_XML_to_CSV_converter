package com.xmlconverter.service;

import com.xmlconverter.model.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class XmlProcessingService {
    @Autowired XmlParserService xmlParserService;
    @Autowired GameService gameService;
    @Autowired CsvGeneratorService csvGeneratorService;

    public byte[] processXmlFile(@NonNull final MultipartFile file) throws Exception {
        try {
            final Games games = xmlParserService.parse(file);
            final Games sortedGames = gameService.getSortedBySalesDesc(games);
            return csvGeneratorService.generateCsv(sortedGames);
        } catch (Exception exception) {
            throw new Exception("Ошибка обработки XML: " + exception.getMessage(), exception);
        }
    }
}