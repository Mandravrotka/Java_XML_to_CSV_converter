package com.example.demo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.Data;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

@XmlRootElement(name = "игры")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Games {
    @XmlElement(name = "игра")
    private List<Game> games;

    public Games getGamesSortedBySalesDesc() {
        if (games == null || games.isEmpty()) {
            return new Games();
        }
        Games sorted = new Games();
        sorted.games = new ArrayList<>(games); // копируем
        sorted.games.sort(Comparator.comparingLong(Game::getSales).reversed());
        return sorted;
    }

    public List<Game> getGames() { return games; }
}