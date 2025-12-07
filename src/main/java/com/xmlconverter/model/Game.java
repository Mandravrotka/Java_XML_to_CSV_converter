package com.xmlconverter.model;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Game {
    @XmlElement(name = "название")
    private final String title;

    @XmlElement(name = "дата")
    private final String releaseDate;

    @XmlElement(name = "жанр")
    private final String genre;

    @XmlElement(name = "рейтинг")
    private final int rating;

    @XmlElement(name = "продажи")
    private final long sales;

    @XmlElement(name = "разработчик")
    private final String developer;

    @XmlElement(name = "возрастной_рейтинг")
    private final String ageRating;
}