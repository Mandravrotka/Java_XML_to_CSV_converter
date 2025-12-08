package com.xmlconverter.validator;

import com.xmlconverter.model.Game;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameValidatorTest {

    @Autowired
    private GameValidator validator;

    @Nested
    @DisplayName("Тесты для поля 'title' (название)")
    class TitleValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если игра null")
        void shouldNotValidate_NullGame() {
            String error = validator.validate(null);
            assertNotNull(error);
            assertEquals("Игра не может быть null", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если title пустой")
        void shouldNotValidate_EmptyTitle() {
            Game game = new Game("", "01-01-2020", "Экшен", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Название игры не должно быть пустым", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если title состоит из пробелов")
        void shouldNotValidate_BlankTitle() {
            Game game = new Game("   ", "01-01-2020", "Экшен", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Название игры не должно быть пустым", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если title null")
        void shouldNotValidate_NullTitle() {
            Game game = new Game(null, "01-01-2020", "Экшен", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Название игры не должно быть пустым", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'releaseDate' (дата)")
    class ReleaseDateValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если releaseDate null")
        void shouldNotValidate_NullReleaseDate() {
            Game game = new Game("Игра", null, "Экшен", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Дата не должна быть пустой", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если releaseDate пустой")
        void shouldNotValidate_BlankReleaseDate() {
            Game game = new Game("Игра", "   ", "Экшен", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Дата не должна быть пустой", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если формат даты неверный")
        void shouldNotValidate_InvalidDateFormat() {
            Game game = new Game("Игра", "2020-01-01", "Экшен", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Дата должна быть в формате DD-MM-YYYY и существовать", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если дата не существует")
        void shouldNotValidate_NonExistentDate() {
            Game game = new Game("Игра", "32-13-2020", "Экшен", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Дата должна быть в формате DD-MM-YYYY и существовать", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если год в будущем")
        void shouldNotValidate_FutureYear() {
            Game game = new Game("Игра", "01-01-2100", "Экшен", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Дата должна быть в формате DD-MM-YYYY и существовать", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'genre' (жанр)")
    class GenreValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если genre пустой")
        void shouldNotValidate_EmptyGenre() {
            Game game = new Game("Игра", "01-01-2020", "", 90, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Жанр не должен быть пустым", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'rating' (рейтинг)")
    class RatingValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если rating < 0")
        void shouldNotValidate_RatingTooLow() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", -1, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Рейтинг должен быть от 0 до 100", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если rating > 100")
        void shouldNotValidate_RatingTooHigh() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", 101, 1000000, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Рейтинг должен быть от 0 до 100", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'sales' (продажи)")
    class SalesValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если sales < 0")
        void shouldNotValidate_NegativeSales() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", 90, -100, "Dev", "17+");
            String error = validator.validate(game);
            assertEquals("Количество продаж должно быть неотрицательным числом", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'developer' (разработчик)")
    class DeveloperValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если developer пустой")
        void shouldNotValidate_EmptyDeveloper() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "", "17+");
            String error = validator.validate(game);
            assertEquals("Разработчик не должен быть пустым", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'ageRating' (возрастной рейтинг)")
    class AgeRatingValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating null")
        void shouldNotValidate_NullAgeRating() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", null);
            String error = validator.validate(game);
            assertEquals("Возрастной рейтинг не должен быть пустым", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating пустой")
        void shouldNotValidate_BlankAgeRating() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "   ");
            String error = validator.validate(game);
            assertEquals("Возрастной рейтинг не должен быть пустым", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating без '+'")
        void shouldNotValidate_NoPlus() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "18");
            String error = validator.validate(game);
            assertEquals("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating содержит нечисловой N")
        void shouldNotValidate_NonNumeric() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "abc+");
            String error = validator.validate(game);
            assertEquals("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если N > 21")
        void shouldNotValidate_TooHigh() {
            Game game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "22+");
            String error = validator.validate(game);
            assertEquals("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21", error);
        }
    }

    @Test
    @DisplayName("Должен пройти валидацию для корректной игры")
    void shouldValidate_ValidGame() {
        Game game = new Game("Зе Витчер 3", "19-05-2015", "Экшен РПГ", 93, 50000000, "Си Ди Проект Ред", "17+");
        String error = validator.validate(game);
        assertNull(error);
    }
}