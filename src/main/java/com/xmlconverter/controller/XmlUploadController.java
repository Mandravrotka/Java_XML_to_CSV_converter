package com.xmlconverter.controller;

import com.xmlconverter.model.Game;
import com.xmlconverter.model.Games;
import com.xmlconverter.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/xml")
public class XmlUploadController {

    @Autowired
    private GameService gameService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> uploadGamesXmlFile(@RequestParam("file") MultipartFile file) {
        try {
            String validationError = validateFile(file);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError.getBytes(StandardCharsets.UTF_8));
            }

            Games games = parseXml(file);
            if (games.getGames() == null || games.getGames().isEmpty()) {
                return ResponseEntity.badRequest().body("Файл не содержит игр".getBytes(StandardCharsets.UTF_8));
            }

            for (Game game : games.getGames()) {
                String gameError = validateGame(game);
                if (gameError != null) {
                    return ResponseEntity.badRequest().body(gameError.getBytes(StandardCharsets.UTF_8));
                }
            }

            Games sortedGames = gameService.getSortedBySalesDesc(games);
            byte[] csvBytes = generateCsv(sortedGames);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=Пример.csv")
                    .header("Content-Type", "text/csv;charset=UTF-8")
                    .body(csvBytes);

        } catch (Exception e) {
            e.printStackTrace();
            String error = "Ошибка: " + e.getMessage();
            return ResponseEntity.status(500)
                    .header("Content-Type", "text/plain;charset=UTF-8")
                    .body(error.getBytes(StandardCharsets.UTF_8));
        }
    }
    private String validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "Файл пустой";
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".xml")) {
            return "Требуется XML-файл";
        }

        return null;
    }

    private String validateGame(Game game) {
        // 1) Название не пустое
        if (game.getTitle() == null || game.getTitle().trim().isEmpty()) {
            return "Название игры не должно быть пустым";
        }

        // 2) Дата не пустая и формат DD-MM-YYYY
        if (game.getReleaseDate() == null || game.getReleaseDate().trim().isEmpty()) {
            return "Дата не должна быть пустой";
        }
        if (!isValidSimpleDate(game.getReleaseDate())) {
            return "Дата должна быть в формате DD-MM-YYYY";
        }

        // 3) Жанр не пуст
        if (game.getGenre() == null || game.getGenre().trim().isEmpty()) {
            return "Жанр не должен быть пустым";
        }

        // 4) Рейтинг: 0–100
        if (game.getRating() < 0 || game.getRating() > 100) {
            return "Рейтинг должен быть от 0 до 100";
        }

        // 5) Продажи: неотрицательное число
        if (game.getSales() < 0) {
            return "Количество продаж должно быть неотрицательным числом";
        }

        // 6) Разработчик не пуст
        if (game.getDeveloper() == null || game.getDeveloper().trim().isEmpty()) {
            return "Разработчик не должен быть пустым";
        }

        // 7) Возрастной рейтинг: формат N+ (0+ до 21+)
        if (game.getAgeRating() == null || game.getAgeRating().trim().isEmpty()) {
            return "Возрастной рейтинг не должен быть пустым";
        }
        if (!isValidAgeRating(game.getAgeRating())) {
            return "Возрастной рейтинг должен быть в формате N+, где N от 0 до 21";
        }

        return null;
    }

    // Проверка формата DD-MM-YYYY
    private boolean isValidSimpleDate(String date) {
        if (date.length() != 10) return false;
        if (date.charAt(2) != '-' || date.charAt(5) != '-') return false;

        String[] parts = date.split("-");
        if (parts.length != 3) return false;

        String day = parts[0], month = parts[1], year = parts[2];

        if (!isNumeric(day) || !isNumeric(month) || !isNumeric(year)) return false;
        if (day.length() != 2 || month.length() != 2 || year.length() != 4) return false;

        int y = Integer.parseInt(year);
        int m = Integer.parseInt(month);
        int d = Integer.parseInt(day);

        // Проверка диапазона года: от 1950 до текущего года
        int currentYear = java.time.Year.now().getValue();
        if (y < 1950 || y > currentYear) return false;

        // Проверка месяца
        if (m < 1 || m > 12) return false;

        // Проверка дня (упрощённо, без високосных)
        if (d < 1 || d > 31) return false;

        return true;
    }

    // Проверка формата N+ (от 0+ до 21+)
    private boolean isValidAgeRating(String rating) {
        if (!rating.endsWith("+")) return false;
        String numPart = rating.substring(0, rating.length() - 1);
        if (numPart.isEmpty()) return false;
        if (!isNumeric(numPart)) return false;

        int n = Integer.parseInt(numPart);
        return n >= 0 && n <= 21;
    }

    // Проверка, что строка — число
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private Games parseXml(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            JAXBContext context = JAXBContext.newInstance(Games.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Games) unmarshaller.unmarshal(inputStream);
        }
    }

    private byte[] generateCsv(Games games) throws IOException {
        ByteArrayOutputStream csvOutput = new ByteArrayOutputStream();
        csvOutput.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});

        try (OutputStreamWriter writer = new OutputStreamWriter(csvOutput, StandardCharsets.UTF_8)) {
            writer.write("название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг\n");

            List<Game> gameList = games.getGames();
            if (gameList != null) {
                for (Game game : gameList) {
                    writer.write(String.format("%s,%s,%s,%d,%d,%s,%s\n",
                            game.getTitle(),
                            game.getReleaseDate(),
                            game.getGenre(),
                            game.getRating(),
                            game.getSales(),
                            game.getDeveloper(),
                            game.getAgeRating()
                    ));
                }
            }
            writer.flush();
        }

        return csvOutput.toByteArray();
    }
}