package com.xmlconverter.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Game {
    @XmlElement(name = "название")
    private String title;

    @XmlElement(name = "дата")
    private String releaseDate;

    @XmlElement(name = "жанр")
    private String genre;

    @XmlElement(name = "рейтинг")
    private int rating;

    @XmlElement(name = "продажи")
    private long sales;

    @XmlElement(name = "разработчик")
    private String developer;

    @XmlElement(name = "возрастной_рейтинг")
    private String ageRating;

    // JAXB требует публичный конструктор по умолчанию
    public Game() {}
}