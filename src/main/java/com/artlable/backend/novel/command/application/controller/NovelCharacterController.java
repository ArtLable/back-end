package com.artlable.backend.novel.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelCreateCharacter;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelReadCharacter;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelUpdateCharacter;
import com.artlable.backend.novel.command.domain.service.NovelCharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags= "CHARACTER API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NovelCharacterController {

    private final NovelCharacterService novelCharacterService;

    @ApiOperation(value = "전체 캐릭터 조회")
    @GetMapping("/characters")
    public ResponseEntity<?> findAllCharacters() {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<NovelReadCharacter> characters = novelCharacterService.findAllNovelCharacters();
            responseMap.put("characters", characters);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "전체 캐릭터 조회 성공!", responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "캐릭터 번호로 조회")
    @GetMapping("/characters/{characterNo}")
    public ResponseEntity<?> findCharactersById(@PathVariable Long characterNo) {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            NovelReadCharacter readCharacter = novelCharacterService.findCharacter(characterNo);
            responseMap.put("characters", readCharacter);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일 캐릭터 조회 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "캐릭터 생성")
    @PostMapping("/characters")
    public ResponseEntity<?> createCharacter(@RequestBody NovelCreateCharacter createCharacter,
                                         @RequestHeader("Authorization") String accessToken) {

        try {
            Long characterNo = novelCharacterService.createCharacter(createCharacter, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("characterNo",characterNo);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "캐릭터 생성 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "캐릭터 수정")
    @PutMapping("/characters/{characterNo}")
    public ResponseEntity<?> modifyInfo(@PathVariable Long characterNo,
                                        @RequestBody NovelUpdateCharacter updateCharacter,
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
