package com.artlable.backend.member.command.application.controller;

import com.artlable.backend.member.command.application.dto.login.LoginRequestDTO;
import com.artlable.backend.member.command.application.dto.login.LoginResponseDTO;
import com.artlable.backend.member.command.application.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(tags= "Login API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @ApiOperation(value = "로그인 요청")
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO, HttpServletResponse response) throws Exception{

        Map<String, Object> loginResult = loginService.login(requestDTO);
        LoginResponseDTO loginResponse = (LoginResponseDTO) loginResult.get("loginResponse");

        // 리프레시 토큰을 HTTP Only 쿠키에 설정
        String refreshToken = (String) loginResult.get("refreshToken");
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(loginResponse);
    }

    @ApiOperation(value = "로그아웃 요청") // 쿠키에 refresh토큰 삭제
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // 리프레시 토큰 쿠키 삭제
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // 쿠키 만료
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().build();
    }
}
