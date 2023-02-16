package com.example.telegrambot2023.service;

import com.example.telegrambot2023.dto.JsoupResponseType;
import com.example.telegrambot2023.dto.TatoebaData;
import com.example.telegrambot2023.repository.TatoRepo;
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

        JsoupResponseType data = service.generateData(word, from, to);

        return data.toString();

    }

}
