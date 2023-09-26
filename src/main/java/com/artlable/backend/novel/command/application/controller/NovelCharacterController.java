package com.artlable.backend.novel.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.files.command.application.service.FileService;
import com.artlable.backend.files.command.application.service.ResultFileService;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelCreateCharacterDTO;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelReadCharacterDTO;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelResultCharacterDTO;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelUpdateCharacterDTO;
import com.artlable.backend.novel.command.domain.service.NovelCharacterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags= "NOVEL CHARACTER API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NovelCharacterController {

    private final NovelCharacterService novelCharacterService;
    private final ResultFileService resultFileService;

    @ApiOperation(value = "전체 캐릭터 조회")
    @GetMapping("/characters")
    public ResponseEntity<ResponseMessage> findAllCharacters() {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<NovelReadCharacterDTO> characters = novelCharacterService.findAllNovelCharacters();
            responseMap.put("characters", characters);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "전체 캐릭터 조회 성공!", responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "캐릭터 번호로 조회")
    @GetMapping("/characters/{characterNo}")
    public ResponseEntity<ResponseMessage> findCharactersById(@PathVariable Long characterNo) {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            NovelReadCharacterDTO readCharacter = novelCharacterService.findCharacter(characterNo);
            responseMap.put("characters", readCharacter);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일 캐릭터 조회 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "캐릭터 생성")
    @PostMapping(value = "/characters")
    public ResponseEntity<ResponseMessage> createCharacter(
            @RequestBody NovelCreateCharacterDTO requestDTO,
            @RequestHeader("Authorization") String accessToken) {

        try {
            Long characterNo = novelCharacterService.createCharacter(requestDTO, accessToken);

            // ResultFileService의 saveImage 메서드 호출
            List<NovelResultCharacterDTO> resultCharacterDTOs = resultFileService.saveImage(requestDTO, accessToken, characterNo);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("characterNo", characterNo);
            responseMap.put("resultCharacters", resultCharacterDTOs); // 결과도 같이 반환

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "캐릭터 생성 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @ApiOperation(value = "캐릭터 수정")
    @PutMapping("/characters/{characterNo}")
    public ResponseEntity<?> modifyInfo(@PathVariable Long characterNo,
                                        @RequestBody NovelUpdateCharacterDTO updateCharacter,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultCharacterNo = novelCharacterService.updateCharacter(characterNo, updateCharacter, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("characterNo", resultCharacterNo);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "캐릭터 수정 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @ApiOperation(value = "캐릭터 삭제")
    @DeleteMapping("/characters/{characterNo}")
    public ResponseEntity<?> removeCharacter(@PathVariable Long characterNo,
                                             @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultCharacterNo = novelCharacterService.deleteCharacter(characterNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("characterNo", resultCharacterNo);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "캐릭터 삭제 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
}
