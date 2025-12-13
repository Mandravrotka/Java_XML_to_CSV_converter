package com.xmlconverter.model;

import jakarta.xml.bind.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;
import static lombok.AccessLevel.PUBLIC;

@XmlAccessorType(FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @XmlElement(name = "название")
    String title;

    @XmlElement(name = "дата")
    String releaseDate;

    @XmlElement(name = "жанр")
    String genre;

    @XmlElement(name = "рейтинг")
    int rating;

    @XmlElement(name = "продажи")
    long sales;

    @XmlElement(name = "разработчик")
    String developer;

    @XmlElement(name = "возрастной_рейтинг")
    String ageRating;
}