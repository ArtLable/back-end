package com.artlable.backend.member.command.application.controller;

import com.artlable.backend.member.command.application.dto.sign.SignRequestDTO;
import com.artlable.backend.member.command.application.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags= "Member CRUD API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final SignService signService;

    @ApiOperation(value = "일반 회원가입")
    @PostMapping(value = "/signup")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequestDTO requestDTO) throws Exception{
        return new ResponseEntity<>(signService.register(requestDTO),HttpStatus.OK);
    }
}
