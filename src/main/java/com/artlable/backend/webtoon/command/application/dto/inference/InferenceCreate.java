package com.artlable.backend.webtoon.command.application.dto.inference;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Inference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InferenceCreate {

    private String inferenceContent;
    private String inferenceResult;
    private List<Files> files;

    @Builder
    public InferenceCreate(String inferenceContent, String inferenceResult, List<Files> files) {
        this.inferenceContent = inferenceContent;
        this.inferenceResult = inferenceResult;
        this.files = files;
    }

    public Inference toEntity() {
        return Inference.builder()
                .inferenceContent(inferenceContent)
                .inferenceResult(inferenceResult)
                .files(files)
                .build();
    }
}
