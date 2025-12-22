package com.xmlconverter.validator;

import com.xmlconverter.model.Game;
import lombok.val;
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
        @DisplayName("Должен вернуть ошибку, если title пустой")
        void shouldNotValidate_EmptyTitle() {
            val game = new Game("", "01-01-2020", "Экшен", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Название игры не должно быть пустым", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если title состоит из пробелов")
        void shouldNotValidate_BlankTitle() {
            val game = new Game("   ", "01-01-2020", "Экшен", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Название игры не должно быть пустым", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если title null")
        void shouldNotValidate_NullTitle() {
            val game = new Game(null, "01-01-2020", "Экшен", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Название игры не должно быть пустым", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'releaseDate' (дата)")
    class ReleaseDateValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если releaseDate null")
        void shouldNotValidate_NullReleaseDate() {
            val game = new Game("Игра", null, "Экшен", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Дата релиза не должна быть пустой", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если releaseDate пустой")
        void shouldNotValidate_BlankReleaseDate() {
            val game = new Game("Игра", "   ", "Экшен", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Дата релиза не должна быть пустой", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если формат даты неверный")
        void shouldNotValidate_InvalidDateFormat() {
            val game = new Game("Игра", "2020-01-01", "Экшен", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Дата должна быть в формате DD-MM-YYYY", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если дата не существует")
        void shouldNotValidate_NonExistentDate() {
            val game = new Game("Игра", "32-13-2020", "Экшен", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Указана некорректная дата", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если год в будущем")
        void shouldNotValidate_FutureYear() {
            val game = new Game("Игра", "01-01-2100", "Экшен", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Год должен быть от 1950 до 2025", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'genre' (жанр)")
    class GenreValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если genre пустой")
        void shouldNotValidate_EmptyGenre() {
            val game = new Game("Игра", "01-01-2020", "", 90, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Жанр не должен быть пустым", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'rating' (рейтинг)")
    class RatingValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если rating < 0")
        void shouldNotValidate_RatingTooLow() {
            val game = new Game("Игра", "01-01-2020", "Экшен", -1, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Рейтинг должен быть от 0 до 100", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если rating > 100")
        void shouldNotValidate_RatingTooHigh() {
            val game = new Game("Игра", "01-01-2020", "Экшен", 101, 1000000, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Рейтинг должен быть от 0 до 100", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'sales' (продажи)")
    class SalesValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если sales < 0")
        void shouldNotValidate_NegativeSales() {
            val game = new Game("Игра", "01-01-2020", "Экшен", 90, -100, "Dev", "17+");
            val error = validator.validate(game);
            assertEquals("Количество продаж должно быть неотрицательным числом", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'developer' (разработчик)")
    class DeveloperValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если developer пустой")
        void shouldNotValidate_EmptyDeveloper() {
            val game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "", "17+");
            val error = validator.validate(game);
            assertEquals("Разработчик не должен быть пустым", error);
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'ageRating' (возрастной рейтинг)")
    class AgeRatingValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating null")
        void shouldNotValidate_NullAgeRating() {
            val game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", null);
            val error = validator.validate(game);
            assertEquals("Возрастной рейтинг не должен быть пустым", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating пустой")
        void shouldNotValidate_BlankAgeRating() {
            val game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "   ");
            val error = validator.validate(game);
            assertEquals("Возрастной рейтинг не должен быть пустым", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating без '+'")
        void shouldNotValidate_NoPlus() {
            val game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "18");
            val error = validator.validate(game);
            assertEquals("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating содержит нечисловой N")
        void shouldNotValidate_NonNumeric() {
            val game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "abc+");
            val error = validator.validate(game);
            assertEquals("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21", error);
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если N > 21")
        void shouldNotValidate_TooHigh() {
            val game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "22+");
            val error = validator.validate(game);
            assertEquals("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21", error);
        }
    }

    @Test
    @DisplayName("Должен пройти валидацию для корректной игры")
    void shouldValidate_ValidGame() {
        val game = new Game("Зе Витчер 3", "19-05-2015", "Экшен РПГ", 93, 50000000, "Си Ди Проект Ред", "17+");
        val error = validator.validate(game);
        assertNull(error);
    }
}