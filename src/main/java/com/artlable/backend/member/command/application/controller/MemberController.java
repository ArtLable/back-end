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
    public ResponseEntity<ResponseMessage> signup(@Valid @RequestBody SignRequestDTO requestDTO) {
        try{
            signService.register(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "회원가입 완료",null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "닉네임 중복 조회")
    @GetMapping(value="/members/nickname/{memberNickname}/check")
    public ResponseEntity<ResponseMessage> checkMemberNickname(@PathVariable String memberNickname) {
        try {
            signService.checkDuplicateMemberNickname(memberNickname);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "사용 가능한 닉네임 입니다.",null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "이메일 중복 조회")
    @GetMapping(value="/members/email/{memberEmail}/check")
    public ResponseEntity<ResponseMessage> checkMemberEmail(@PathVariable String memberEmail) {
        try {
            signService.checkDuplicateMemberEmail(memberEmail);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "사용 가능한 이메일 입니다.",null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/members/pwd/{memberNo}")
    public ResponseEntity<ResponseMessage> changePassword(@PathVariable Long memberNo,
                                                          @RequestBody UpdatePwdRequestDTO requestDTO,
                                                          @RequestHeader("Authorization") String accessToken) {
        try {
            signService.changeMemberPwd(requestDTO, memberNo, accessToken);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "비밀번호가 변경되었습니다.",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "회원 정보 변경")
    @PutMapping("/members/info/{memberNo}")
    public ResponseEntity<ResponseMessage> updateMemberInfo(@PathVariable Long memberNo,
                                                            @RequestBody UpdateInfoRequestDTO requestDTO,
                                                            @RequestHeader("Authorization") String accessToken) {
        try {
            signService.updateMemberInfo(requestDTO, memberNo, accessToken);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "회원정보가 변경되었습니다.",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping(value="/members/{memberNo}")
    public ResponseEntity<ResponseMessage> deleteMember(@PathVariable Long memberNo,
                                                        @RequestHeader("Authorization") String accessToken) {
        try {
            signService.deleteMember(memberNo, accessToken);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "회원탈퇴가 정상처리되었습니다.",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }
}
