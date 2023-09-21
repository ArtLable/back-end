package com.artlable.backend.file.command.domain.service;

import com.artlable.backend.common.annotation.DomainAnnotation;
import com.artlable.backend.file.command.application.dto.FileResponse;
import com.artlable.backend.file.command.application.dto.SingleFileRequest;

import java.io.IOException;
@DomainAnnotation
public interface FileDomainService {

    FileResponse getAISingleFile(SingleFileRequest singleFileDTO) throws IOException;
}
