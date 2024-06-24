package com.sparta.greeypeople.user.service;

import com.sparta.greeypeople.exception.ConflictException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.user.dto.request.AdminUserAuthRequestDto;
import com.sparta.greeypeople.user.dto.request.AdminUserProfileRequestDto;
import com.sparta.greeypeople.user.dto.response.AdminUserResponseDto;
import com.sparta.greeypeople.user.entity.BlockedUser;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import com.sparta.greeypeople.user.enumeration.UserStatus;
import com.sparta.greeypeople.user.repository.BlockedUserRepository;
import com.sparta.greeypeople.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final BlockedUserRepository blockedUserRepository;

    @Transactional(readOnly = true)
    public List<AdminUserResponseDto> findAllUser() {
        return userRepository.findAll()
            .stream().map(AdminUserResponseDto::new).toList();
    }

    @Transactional
    public AdminUserResponseDto updateUserProfile(Long userId,
        AdminUserProfileRequestDto requestDto) {
        User user = findUser(userId);

        user.updateProfile(requestDto);

        return new AdminUserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findUser(userId);

        userRepository.delete(user);
    }

    @Transactional
    public void updateUserAuth(Long userId, AdminUserAuthRequestDto requestDto) {
        User user = findUser(userId);
        String getUserAuth = user.getUserAuth().toString();

        if (getUserAuth.equals(requestDto.getUserAuth())) {
            throw new ConflictException("해당 사용자의 변경하려고 하는 권한과 현재의 권한이 같습니다.");
        }

        UserAuth userAuth = UserAuth.USER;
        if (requestDto.getUserAuth().equals("ADMIN")){
            userAuth = UserAuth.ADMIN;
        }

        user.updateAuth(userAuth);
    }

    @Transactional
    public void blockUser(Long userId, String reason) {
        User user = findUser(userId);

        user.updateUserStatus(UserStatus.BLOCKED);
        BlockedUser blockedUser = new BlockedUser(user, reason);
        blockedUserRepository.save(blockedUser);
        userRepository.save(user);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new DataNotFoundException("해당 사용자는 존재하지 않습니다.")
        );
    }
}