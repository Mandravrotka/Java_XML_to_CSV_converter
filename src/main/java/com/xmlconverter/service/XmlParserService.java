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
    static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(Games.class);
        } catch (JAXBException exception) {
            throw new ExceptionInInitializerError(
                new RuntimeException("Ошибка инициализации JAXBContext", exception)
            );
        }
    }

    public Games parse(@NonNull final MultipartFile file) throws Exception {
        try (val inputStream = file.getInputStream()) {
            return (Games) JAXB_CONTEXT.createUnmarshaller().unmarshal(inputStream);
        } catch (JAXBException exception) {
            throw new RuntimeException("Ошибка парсинга XML", exception);
        }
    }
}