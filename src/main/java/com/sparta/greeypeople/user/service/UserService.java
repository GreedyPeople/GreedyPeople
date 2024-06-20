package com.sparta.greeypeople.user.service;

import com.sparta.greeypeople.auth.dto.request.LoginRequestDto;
import com.sparta.greeypeople.auth.dto.request.SignupRequestDto;
import com.sparta.greeypeople.auth.dto.response.TokenResponseDto;

public interface UserService {

    // 사용자 회원가입 처리
    void signup(SignupRequestDto signupRequest);

    // 사용자 로그인 처리 및 토큰 발급
    TokenResponseDto login(LoginRequestDto loginRequest);

    // 사용자 로그아웃 처리
    void logout(String userId);

    // 사용자 회원탈퇴 처리
    void withdraw(String userId, String password);

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
    TokenResponseDto refresh(String refreshToken);
}
