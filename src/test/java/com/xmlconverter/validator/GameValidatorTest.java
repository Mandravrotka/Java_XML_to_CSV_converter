package com.xmlconverter.validator;

import com.xmlconverter.model.Game;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameValidatorTest {
    @Autowired
    private GameValidator validator;

    private Game validGame() {
        return new Game("Зе Витчер 3", "19-05-2015", "Экшен РПГ", 93, 50_000_000, "Си Ди Проект Ред", "17+");
    }

    private Game withTitle(String title) {
        return validGame().toBuilder().title(title).build();
    }

    private Game withReleaseDate(String releaseDate) {
        return validGame().toBuilder().releaseDate(releaseDate).build();
    }

    private Game withGenre(String genre) {
        return validGame().toBuilder().genre(genre).build();
    }

    private Game withRating(int rating) {
        return validGame().toBuilder().rating(rating).build();
    }

    private Game withSales(long sales) {
        return validGame().toBuilder().sales(sales).build();
    }

    private Game withDeveloper(String developer) {
        return validGame().toBuilder().developer(developer).build();
    }

    private Game withAgeRating(String ageRating) {
        return validGame().toBuilder().ageRating(ageRating).build();
    }

    @Nested
    @DisplayName("Тесты для поля 'title' (название)")
    class TitleValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если title пустой")
        void shouldNotValidate_EmptyTitle() {
            assertThat(validator.validate(withTitle("")))
                .isEqualTo("Название игры не должно быть пустым");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если title состоит из пробелов")
        void shouldNotValidate_BlankTitle() {
            assertThat(validator.validate(withTitle("   ")))
                .isEqualTo("Название игры не должно быть пустым");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если title null")
        void shouldNotValidate_NullTitle() {
            assertThat(validator.validate(withTitle(null)))
                .isEqualTo("Название игры не должно быть пустым");
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'releaseDate' (дата)")
    class ReleaseDateValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если releaseDate null")
        void shouldNotValidate_NullReleaseDate() {
            assertThat(validator.validate(withReleaseDate(null)))
                .isEqualTo("Дата релиза не должна быть пустой");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если releaseDate пустой")
        void shouldNotValidate_BlankReleaseDate() {
            assertThat(validator.validate(withReleaseDate("   ")))
                .isEqualTo("Дата релиза не должна быть пустой");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если формат даты неверный")
        void shouldNotValidate_InvalidDateFormat() {
            assertThat(validator.validate(withReleaseDate("2020-01-01")))
                .isEqualTo("Дата должна быть в формате DD-MM-YYYY");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если дата не существует")
        void shouldNotValidate_NonExistentDate() {
            assertThat(validator.validate(withReleaseDate("32-13-2020")))
                .isEqualTo("Указана некорректная дата");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если год в будущем")
        void shouldNotValidate_FutureYear() {
            assertThat(validator.validate(withReleaseDate("01-01-2100")))
                .isEqualTo("Год должен быть от 1950 до 2025");
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'genre' (жанр)")
    class GenreValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если genre пустой")
        void shouldNotValidate_EmptyGenre() {
            assertThat(validator.validate(withGenre("")))
                .isEqualTo("Жанр не должен быть пустым");
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'rating' (рейтинг)")
    class RatingValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если rating < 0")
        void shouldNotValidate_RatingTooLow() {
            assertThat(validator.validate(withRating(-1)))
                .isEqualTo("Рейтинг должен быть от 0 до 100");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если rating > 100")
        void shouldNotValidate_RatingTooHigh() {
            assertThat(validator.validate(withRating(101)))
                .isEqualTo("Рейтинг должен быть от 0 до 100");
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'sales' (продажи)")
    class SalesValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если sales < 0")
        void shouldNotValidate_NegativeSales() {
            assertThat(validator.validate(withSales(-100)))
                .isEqualTo("Количество продаж должно быть неотрицательным числом");
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'developer' (разработчик)")
    class DeveloperValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если developer пустой")
        void shouldNotValidate_EmptyDeveloper() {
            assertThat(validator.validate(withDeveloper("")))
                .isEqualTo("Разработчик не должен быть пустым");
        }
    }

    @Nested
    @DisplayName("Тесты для поля 'ageRating' (возрастной рейтинг)")
    class AgeRatingValidationTest {

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating null")
        void shouldNotValidate_NullAgeRating() {
            assertThat(validator.validate(withAgeRating(null)))
                .isEqualTo("Возрастной рейтинг не должен быть пустым");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating пустой")
        void shouldNotValidate_BlankAgeRating() {
            assertThat(validator.validate(withAgeRating("   ")))
                .isEqualTo("Возрастной рейтинг не должен быть пустым");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating без '+'")
        void shouldNotValidate_NoPlus() {
            assertThat(validator.validate(withAgeRating("18")))
                .isEqualTo("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если ageRating содержит нечисловой N")
        void shouldNotValidate_NonNumeric() {
            assertThat(validator.validate(withAgeRating("abc+")))
                .isEqualTo("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21");
        }

        @Test
        @DisplayName("Должен вернуть ошибку, если N > 21")
        void shouldNotValidate_TooHigh() {
            assertThat(validator.validate(withAgeRating("22+")))
                .isEqualTo("Возрастной рейтинг должен быть в формате N+, где N от 0 до 21");
        }
    }

    @Test
    @DisplayName("Должен пройти валидацию для корректной игры")
    void shouldValidate_ValidGame() {
        assertThat(validator.validate(validGame())).isNull();
    }
}