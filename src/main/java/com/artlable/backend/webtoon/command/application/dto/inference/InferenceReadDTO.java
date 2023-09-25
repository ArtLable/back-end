package com.artlable.backend.webtoon.command.application.dto.inference;

import com.artlable.backend.webtoon.command.domain.aggregate.entity.Inference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InferenceReadDTO {

    private Long inferenceNo;
    private String inferenceContent;
    private String inferenceResult;
    private List<Long> fileNo;

    public InferenceReadDTO(Inference inference) {
        this.inferenceNo = inference.getInferenceNo();
        this.inferenceContent = inference.getInferenceContent();
        this.inferenceResult = inference.getInferenceResult();
    }
    @Builder
    public InferenceReadDTO(Long inferenceNo, String inferenceContent, String inferenceResult, List<Long> fileNo) {
        this.inferenceNo = inferenceNo;
        this.inferenceContent = inferenceContent;
        this.inferenceResult = inferenceResult;
        this.fileNo = fileNo;
    }
}
