package com.artlable.backend.member.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.member.command.application.dto.login.LoginRequestDTO;
import com.artlable.backend.member.command.application.dto.login.LoginResponseDTO;
import com.artlable.backend.member.command.application.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(tags= "Login API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @ApiOperation(value = "로그인 요청")
    @PostMapping(value = "/authentication/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO, HttpServletResponse response) throws Exception{

        try{

        Map<String, Object> loginResult = loginService.login(requestDTO);
        LoginResponseDTO loginResponse = (LoginResponseDTO) loginResult.get("loginResponse");

        // 리프레시 토큰을 HTTP Only 헤더에 설정
        String refreshToken = (String) loginResult.get("refreshToken");
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(loginResponse);
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    // LoginController.java

    @ApiOperation(value = "액세스 토큰 재발급 요청")
    @PostMapping("/authentication/renew")
    public ResponseEntity<?> renewAccessToken(HttpServletRequest request) {
        // 쿠키에서 리프레시 토큰을 가져옵니다.
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        if (refreshToken == null) {
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), "로그인이 만료되었습니다.");
            return ResponseEntity.badRequest().body(responseMessage);
        }
        try {
            // 액세스 토큰을 재발급 받습니다.
            String newAccessToken = loginService.renewAccessToken(refreshToken);
            return ResponseEntity.ok(newAccessToken);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }


    @ApiOperation(value = "로그아웃 요청") // 쿠키에 refresh토큰 삭제
    @PostMapping("/authentication/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            // 리프레시 토큰 쿠키 삭제
            Cookie refreshTokenCookie = new Cookie("refreshToken", null);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(0); // 쿠키 만료
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.ok().build();
        } catch(Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }
}
