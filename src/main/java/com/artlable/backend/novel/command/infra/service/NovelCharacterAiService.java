package com.artlable.backend.novel.command.infra.service;

import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelCreateCharacterDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class NovelCharacterAiService {

    private final WebClient.Builder webClientBuilder;


    public byte[] callExternalApiAndSaveResult(NovelCreateCharacterDTO createDto) {
        String apiUrl = "http://192.168.0.23:5050/step1"; // 호출하려는 API의 URL

        //변수랑 매
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", createDto.getCharacterTitle());
        requestBody.put("name", createDto.getCharacterName());
        requestBody.put("gender", createDto.getCharacterGender());
        requestBody.put("appearance", createDto.getCharacterAppearance());
        requestBody.put("personality", createDto.getCharacterPersonality());
        requestBody.put("genre", createDto.getCharacterGenre());

        // API 호출
        byte[] imageBytes = webClientBuilder.build()
                .post() // POST 요청
                .uri(apiUrl)
                .bodyValue(requestBody) // Request Body 설정
                .retrieve()
                .bodyToMono(byte[].class) // Response Body의 타입 설정
                .block(); // 비동기 호출을 동기 방식으로 변경

                return imageBytes;  // 저장된 이미지 파일의 경로 반환

    }
}