package com.xmlconverter.service;

import com.xmlconverter.model.Game;
import com.xmlconverter.model.Games;
import lombok.val;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.apache.commons.csv.CSVFormat.DEFAULT;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class CsvGeneratorService {

    public byte[] generateCsv(Games games) throws IOException {
        try (val csvOutput = new ByteArrayOutputStream();
             val writer = new OutputStreamWriter(csvOutput, UTF_8);
             val csvPrinter = new CSVPrinter(writer,
                     DEFAULT.withHeader("название", "дата", "жанр", "рейтинг", "продажи", "разработчик", "возрастной_рейтинг"))) {

            if (games.getGames() != null) {
                for (Game game : games.getGames()) {
                    csvPrinter.printRecord(
                            game.getTitle(),
                            game.getReleaseDate(),
                            game.getGenre(),
                            game.getRating(),
                            game.getSales(),
                            game.getDeveloper(),
                            game.getAgeRating()
                    );
                }
            }
            // Если не добавлять flush, то в csvOutput будет храниться максимум 1024 байт.
            // Где-то в процессе выполнения следующей последовательности close() происходит потеря данных.
            // 1. csvPrinter.close() > flush > в writer
            // 2. writer.close() > flush > в csvOutput
            // 3. csvOutput.toByteArray()
            //
            // При добавлении flush, данные в csvOutput будут сохраняться.
            csvPrinter.flush();

            return csvOutput.toByteArray();
        }
    }
}