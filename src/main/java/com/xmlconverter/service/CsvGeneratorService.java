package com.xmlconverter.service;

import com.xmlconverter.model.Games;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.apache.commons.csv.CSVFormat.DEFAULT;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@FieldDefaults(makeFinal = true)
public class CsvGeneratorService {
    final CSVFormat csvFormat = DEFAULT
        .withHeader("название", "дата", "жанр", "рейтинг", "продажи", "разработчик", "возрастной_рейтинг");

    public byte[] generateCsv(@NonNull final Games games) {
        try (val csvOutput = new ByteArrayOutputStream();
             val writer = new OutputStreamWriter(csvOutput, UTF_8);
             val csvPrinter = new CSVPrinter(writer, csvFormat)) {

            games.getGames().forEach(game -> {
                try {
                    csvPrinter.printRecord(
                        game.getTitle(),
                        game.getReleaseDate(),
                        game.getGenre(),
                        game.getRating(),
                        game.getSales(),
                        game.getDeveloper(),
                        game.getAgeRating()
                    );
                } catch (IOException thrown) {
                    throw new RuntimeException("Ошибка при записи данных в CSV", thrown);
                }
            });

            // Явный flush необходим, так как при закрытии CSVPrinter
            // некоторые данные могут оставаться в буфере
            csvPrinter.flush();
            return csvOutput.toByteArray();
        } catch (IOException thrown) {
            throw new RuntimeException("Ошибка при генерации CSV", thrown);
        }
    }
}