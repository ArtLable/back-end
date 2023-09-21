package com.artlable.backend.file.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AllFileResponse implements Serializable {

    private final MultipartFile aiFile;
}
