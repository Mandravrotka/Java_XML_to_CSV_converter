package com.xmlconverter.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@XmlAccessorType(FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
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