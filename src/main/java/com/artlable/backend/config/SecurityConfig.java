package com.artlable.backend.config;

import com.artlable.backend.jwt.JwtAccessDeniedHandler;
import com.artlable.backend.jwt.JwtAuthenticationEntryPoint;
import com.artlable.backend.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                //토큰사용시 CSRF 설정 Disable

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // exception handling

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 세션을 사용하지 않기 때문에 STATELESS로 설정

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //단순 접근
                .antMatchers("/api/v1/authentication/**").permitAll() //로그인
                .antMatchers("/api/v1/members/**").permitAll() //회원
                .antMatchers("/api/v1/feeds/**").permitAll() //피드
                .antMatchers("/api/v1/files/**").permitAll() //파일
                .antMatchers("/api/v1/novels/**").permitAll() // 소설
                .antMatchers("/api/v1/characters/**").permitAll() // 캐릭터
                .antMatchers("/api/v1/summaries/**").permitAll() // 요약
                .antMatchers("/api/v1/webtoons/**").permitAll() // 웹툰
                .antMatchers( //swagger
                        "/swagger-ui/**",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()
//                .antMatchers(
//                예외처리 필요할때 활성화
//                )
                .anyRequest().authenticated() // 그 외 인증 없이 접근X
                .and()
                .apply(new JwtConfig(tokenProvider)); // JwtFilter를 addFilterBefore로 등록했던 JwtConfig class 적용


        return httpSecurity.build();
    }
}
