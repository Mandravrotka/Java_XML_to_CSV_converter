package com.xmlconverter.service;

import lombok.val;
import com.xmlconverter.model.Games;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

@Service
public class XmlParserService {
    private final JAXBContext jaxbContext;

    public XmlParserService() {
        try {
            this.jaxbContext = JAXBContext.newInstance(Games.class);
        } catch (JAXBException thrown) {
            throw new ExceptionInInitializerError(
                new RuntimeException("Ошибка инициализации JAXBContext", thrown));
        }
    }

    public Games parse(@NonNull final MultipartFile file) throws Exception {
        try (val inputStream = file.getInputStream()) {
            return (Games) jaxbContext.createUnmarshaller().unmarshal(inputStream);
        } catch (JAXBException thrown) {
            throw new RuntimeException("Ошибка парсинга XML", thrown);
        }
    }
}