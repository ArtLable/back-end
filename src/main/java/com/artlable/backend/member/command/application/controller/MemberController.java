package com.artlable.backend.member.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.member.command.application.dto.sign.SignRequestDTO;
import com.artlable.backend.member.command.application.dto.update.UpdateInfoRequestDTO;
import com.artlable.backend.member.command.application.dto.update.UpdatePwdRequestDTO;
import com.artlable.backend.member.command.application.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags= "Member CRUD API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final SignService signService;

    @ApiOperation(value = "일반 회원가입")
    @PostMapping(value = "/members")
    public ResponseEntity<?> signup(@Valid @RequestBody SignRequestDTO requestDTO) {
        try{
            signService.register(requestDTO);
            return  ResponseEntity.ok("회원가입 완료");
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @ApiOperation(value = "닉네임 중복 조회")
    @GetMapping(value="/members/nickname/{memberNickname}/check")
    public ResponseEntity<?> checkMemberNickname(@PathVariable String memberNickname) {
        try {
            signService.checkDuplicateMemberNickname(memberNickname);
            return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @ApiOperation(value = "이메일 중복 조회")
    @GetMapping(value="/members/email/{memberEmail}/check")
    public ResponseEntity<?> checkMemberEmail(@PathVariable String memberEmail) {
        try {
            signService.checkDuplicateMemberEmail(memberEmail);
            return ResponseEntity.ok("사용 가능한 이메일 입니다..");
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/members/pwd/{memberNo}")
    public ResponseEntity<?> changePassword(@PathVariable Long memberNo, @RequestBody UpdatePwdRequestDTO requestDTO) {
        try {
            signService.changeMemberPwd(requestDTO, memberNo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @ApiOperation(value = "회원 정보 변경")
    @PutMapping("/members/info/{memberNo}")
    public ResponseEntity<?> updateMemberInfo(@PathVariable Long memberNo, @RequestBody UpdateInfoRequestDTO requestDTO) {
        try {
            signService.updateMemberInfo(requestDTO, memberNo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping(value="/members/{memberNo}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberNo) {
        try {
            signService.deleteMember(memberNo);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
}
