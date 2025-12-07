package com.xmlconverter.service;

import com.xmlconverter.model.Games;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;

@Service
public class XmlParserService {
    // JAXBContext является потокобезопасным и должен быть создан только один раз и повторно использован,
    // чтобы избежать затрат на инициализацию метаданных несколько раз. Marshaller и Unmarshaller не потокобезопасны.

    // Кешируем JAXBContext
    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(Games.class);
        } catch (JAXBException exception) {
            throw new ExceptionInInitializerError("Ошибка инициализации JAXBContext: " + exception.getMessage());
        }
    }

    public Games parse(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            Unmarshaller unmarshaller = JAXB_CONTEXT.createUnmarshaller();
            return (Games) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException exception) {
            throw new RuntimeException("Ошибка парсинга XML", exception);
        }
    }
}