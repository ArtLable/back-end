package com.artlable.backend.file.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
public class FileResponse implements Serializable {

    private final String aiFile;
}
