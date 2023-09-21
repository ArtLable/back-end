package com.artlable.backend.file.command.application.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileListReqeust {

    private List<MultipartFile> webFileList;
    private List<MultipartFile> nobleFileList;
}
