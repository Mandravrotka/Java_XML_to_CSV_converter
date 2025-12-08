//package com.xmlconverter.controller;
//
//import com.xmlconverter.model.Game;
//import com.xmlconverter.model.Games;
//import com.xmlconverter.service.XmlParserService;
//import com.xmlconverter.service.CsvGeneratorService;
//import com.xmlconverter.validator.XmlUploadValidator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//@WebMvcTest(XmlUploadController.class)
//@Import({XmlParserService.class, CsvGeneratorService.class, XmlUploadValidator.class})
//@ExtendWith(MockitoExtension.class)
//class XmlUploadControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private XmlParserService xmlParserService;
//
//    @Mock
//    private CsvGeneratorService csvGeneratorService;
//
//    @Mock
//    private XmlUploadValidator xmlUploadValidator;
//
//    private MockMultipartFile correctFile;
//
//    @BeforeEach
//    void setUp() {
//        String correctXml = """
//                <?xml version="1.0" encoding="UTF-8"?>
//                <игры>
//                    <игра>
//                        <название>Игра</название>
//                        <дата>01-01-2020</дата>
//                        <жанр>Экшен</жанр>
//                        <рейтинг>90</рейтинг>
//                        <продажи>1000000</продажи>
//                        <разработчик>Dev</разработчик>
//                        <возрастной_рейтинг>17+</возрастной_рейтинг>
//                    </игра>
//                </игры>
//                """;
//
//        correctFile = new MockMultipartFile(
//                "file",
//                "Пример.xml",
//                "application/xml",
//                correctXml.getBytes(UTF_8)
//        );
//
//        Game game = new Game("Игра", "01-01-2020", "Экшен", 90, 1000000, "Dev", "17+");
//        Games games = new Games(Collections.singletonList(game));
//
//        try {
//            given(xmlParserService.parse(correctFile)).willReturn(games);
//            given(csvGeneratorService.generateCsv(games)).willReturn(
//                    "название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг\nИгра,01-01-2020,Экшен,90,1000000,Dev,17+\n".getBytes(UTF_8)
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("Ошибка при настройке моков", e);
//        }
//
//        given(xmlUploadValidator.validate(correctFile)).willReturn(null);
//    }
//
//    @Test
//    @DisplayName("Должен вернуть CSV при валидном файле")
//    void shouldReturnCsv_WhenFileIsValid() throws Exception {
//        String expectedCsv = "название,дата,жанр,рейтинг,продажи,разработчик,возрастной_рейтинг\nИгра,01-01-2020,Экшен,90,1000000,Dev,17+\n";
//
//        mockMvc.perform(multipart("/xml/upload")
//                        .file(correctFile))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/csv;charset=UTF-8"))
//                .andExpect(header().string("Content-Disposition", "attachment; filename=Example.csv"))
//                .andExpect(content().bytes(expectedCsv.getBytes(UTF_8)));
//    }
//}