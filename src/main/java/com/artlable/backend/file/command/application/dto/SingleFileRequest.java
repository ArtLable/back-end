package com.artlable.backend.file.command.application.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SingleFileRequest {

    private MultipartFile webToonSingleFile;
    private MultipartFile nobleSingleFile;

    public void setWebToonSingleFile(MultipartFile webToonSingleFile) {
    }

    public void setNobleSingleFile(MultipartFile nobleSingleFile) {
    }
}
