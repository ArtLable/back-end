package com.artlable.backend.webtoon.command.infra.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class BASE64DecodedMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(",")[0];
    }

    @Override
    public String getName() {
        // TODO: 구현
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1].split(";")[0];
    }

    @Override
    public String getOriginalFilename() {
        // TODO: 구현
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1].split(";")[0];
    }

    @Override
    public String getContentType() {
        // TODO: 구현
        return header.split(":")[1].split(";")[0];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}
