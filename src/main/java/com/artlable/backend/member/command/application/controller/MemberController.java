package com.artlable.backend.member.command.application.controller;

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
    public ResponseEntity<Boolean> signup(@Valid @RequestBody SignRequestDTO requestDTO) throws Exception{
        return new ResponseEntity<>
                (signService.register(requestDTO),HttpStatus.OK);
    }

    @ApiOperation(value = "닉네임 중복 조회")
    @GetMapping(value="/members/nickname/{memberNickname}/check")
    public ResponseEntity<String> checkMemberNickname(@PathVariable String memberNickname) throws Exception {
        signService.checkDuplicateMemberNickname(memberNickname);
        return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
    }

    @ApiOperation(value = "이메일 중복 조회")
    @GetMapping(value="/members/email/{memberEmail}/check")
    public ResponseEntity<String> checkMemberEmail(@PathVariable String memberEmail) throws Exception {
        signService.checkDuplicateMemberEmail(memberEmail);
        return ResponseEntity.ok("사용 가능한 이메일 입니다..");
    }

    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/members/pwd/{memberNo}")
    public ResponseEntity<String> changePassword(@PathVariable Long memberNo, @RequestBody UpdatePwdRequestDTO requestDTO) {
        try {
            signService.changeMemberPwd(requestDTO, memberNo);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "회원 정보 변경")
    @PutMapping("/members/info/{memberNo}")
    public ResponseEntity<String> updateMemberInfo(@PathVariable Long memberNo, @RequestBody UpdateInfoRequestDTO requestDTO) {
        try {
            signService.updateMemberInfo(requestDTO, memberNo);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping(value="/members/{memberNo}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberNo) {
        try {
            signService.deleteMember(memberNo);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
