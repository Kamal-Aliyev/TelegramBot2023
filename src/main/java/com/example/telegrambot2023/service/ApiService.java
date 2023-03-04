package com.example.telegrambot2023.service;

import com.example.telegrambot2023.dto.TelegramResponseType;
import com.example.telegrambot2023.repository.TatoRepo;
import com.example.telegrambot2023.securityconfig.WebSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ApiService {

    @Autowired
    TatoRepo repository;



    private final JsoupService service;

    public String gtExample(String word, String from, String to) throws IOException {
        String languageCode = "az";
        TelegramResponseType data = service.generateData(languageCode,word, from, to);

        return data.toString();


    }

}
