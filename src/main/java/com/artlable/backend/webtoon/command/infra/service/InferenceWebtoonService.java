package com.artlable.backend.webtoon.command.infra.service;

import com.artlable.backend.common.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class InferenceWebtoonService {

    private final String baseUrl = "http://192.168.0.6:8000/file_upload";
    private final WebClient webClient;


    @Autowired
    public InferenceWebtoonService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(this.baseUrl).build();
    }

   //
    public ResponseMessage WebSendFilesAndMessage(List<String> filePaths, String message) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("text_input", message);

        for (int i = 0; i < filePaths.size(); i++) {
            body.add("file" + i, new FileSystemResource(filePaths.get(i)));
        }

        return webClient.post()
                .uri(baseUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    // 4xx 에러 처리
                    return Mono.error(new RuntimeException("Client Error!"));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    // 5xx 에러 처리
                    return Mono.error(new RuntimeException("Server Error!"));
                })
                .bodyToMono(ResponseMessage.class)
                .block();
    }
}
