package com.xmlconverter.model;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.util.List;

import static jakarta.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "игры")
@XmlAccessorType(FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Games {
    @XmlElement(name = "игра")
    List<Game> games;
}