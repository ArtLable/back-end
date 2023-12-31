package com.artlable.backend.webtoon.command.application.dto.learning;

import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningCreateDTO {

    private String cname;
    private String searchText;
    private List<MultipartFile> files;

    @Builder
    public LearningCreateDTO(String cname, String searchText, List<MultipartFile> files) {
        this.cname = cname;
        this.searchText = searchText;
        this.files = files;
    }

    public Learning toEntity() {
        return Learning.builder()
                .canme(cname)
                .searchText(searchText)
                .build();
    }
}
