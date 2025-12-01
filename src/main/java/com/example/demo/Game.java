package com.example.demo;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

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

    // Lombok не хочет рабоать
    public String getTitle() { return title; }
    public String getReleaseDate() { return releaseDate; }
    public String getGenre() { return genre; }
    public int getRating() { return rating; }
    public long getSales() { return sales; }
    public String getDeveloper() { return developer; }
    public String getAgeRating() { return ageRating; }
}