package com.example.telegrambot2023.Jsoup;

import com.example.telegrambot2023.dto.TelegramResponseType;
import com.example.telegrambot2023.service.JsoupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class Parsing {

    private final JsoupService jsoupService;


    public TelegramResponseType convert(String languageCode, String word, String from, String to) throws IOException {


        return jsoupService.generateData(languageCode,word, from, to);


    }
}



