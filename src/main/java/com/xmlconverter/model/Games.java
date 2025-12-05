package com.xmlconverter.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;
import lombok.Data;

@XmlRootElement(name = "игры")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Games {
    @XmlElement(name = "игра")
    private List<Game> games;

    public Games() {}
}