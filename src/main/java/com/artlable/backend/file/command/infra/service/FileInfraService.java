package com.artlable.backend.file.command.infra.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.artlable.backend.common.annotation.InfraAnnotation;
import com.artlable.backend.file.command.application.dto.FileResponse;
import com.artlable.backend.file.command.application.dto.SingleFileRequest;
import com.artlable.backend.file.command.domain.service.FileDomainService;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@InfraAnnotation
public class FileInfraService implements FileDomainService {
    private final String REQUEST_URL = "http://192.168.0.63";  // ai 이미지 생성 api url
    private final WebClient WEBCLIENT = WebClient.builder().baseUrl(REQUEST_URL).build();

    @Override
    public FileResponse getAISingleFile(SingleFileRequest singleFileDTO) throws IOException {

        //      HttpURLConnection 방식
//        try {
//            // Set header
//            Map<String, String> headers = new HashMap<>();
//            HttpPostMultipart multipart = new HttpPostMultipart(reqURL, "utf-8", headers);
//
//            // Add form field
////            multipart.addFormField("username", "test_name");
////            multipart.addFormField("password", "test_psw");
//
//            // Add file
//            multipart.addFilePart("imgFile", new File(imagesDTO.getMaleImage()));
//            multipart.addFilePart("imgFile", new File(imagesDTO.getFemaleImage()));
//
//            // Print result
//            String response = multipart.finish();
//            System.out.println(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // WebClient 방식
        MultipartBodyBuilder formData = new MultipartBodyBuilder();
        formData.part("imgFile", singleFileDTO.getWebToonSingleFile().getResource());
        formData.part("imgFile", singleFileDTO.getNobleSingleFile().getResource());

        String response = WEBCLIENT.post()
                .uri("/") // baseUrl 이후 uri
                .accept(MediaType.MULTIPART_FORM_DATA)
                .contentType(MediaType.MULTIPART_FORM_DATA) // 보내는 자원의 형식(header에 담김)
                .body(BodyInserters.fromMultipartData(formData.build())) // 요청 body
                .retrieve() // ResponseEntity를 받아 디코딩
                .bodyToMono(String.class) // 0~1개의 결과 리턴
                .block(); // blocking

        JsonElement element = JsonParser.parseString(response);
        String fileData = element.getAsJsonObject().get("file_data").getAsString();
        String filePath = "/AiFiles/" + UUID.randomUUID() + ".png"; // Ai 이미지 로컬 저장 경로

        // Base64 문자열을 바이트 배열로 변환
        byte[] imageBytes = Base64.getDecoder().decode(fileData);

        // 바이트 배열을 BufferedImage 객체로 변환
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);

            // BufferedImage 객체를 이미지 파일로 저장
            ImageIO.write(image, "jpg, png", new File("src/main/webapp" + filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FileResponse(filePath);
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }
}

