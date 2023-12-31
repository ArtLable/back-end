package com.artlable.backend.webtoon.command.infra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.IOException;
import java.util.List;

@Service
public class LearningWebtoonService {

    private final String baseUrl = "http://192.168.0.23:5000/pcollection";
    private final WebClient webClient;

    @Autowired
    public LearningWebtoonService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(this.baseUrl).build();
    }

    //모델학습 (사진4개 String 으로 반환)
    public List<String> sendFilesAndRetrieveImages(List<MultipartFile> files, String cname, String search_text) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("cname", cname);
        body.add("search_text", search_text);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            body.add("file" + i, new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });
        }

        return webClient.post()
                .uri(baseUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .bodyToFlux(String.class) // Base64 인코딩된 문자열을 직접 받음
                .collectList()
                .block();
    }
}
