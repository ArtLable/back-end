package com.artlable.backend.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64ToFile {

    public void saveBase64AsFile(String base64String, String filePath) {
        try {
            // Base64 문자열 디코딩
            byte[] fileBytes = Base64.getDecoder().decode(base64String);

            // 디코딩된 바이트 배열을 파일로 저장
            try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
                fos.write(fileBytes);
            }
        } catch (IOException e) {
            e.printStackTrace(); // 적절한 예외 처리를 수행하세요.
        }
    }
}
