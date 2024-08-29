package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BaseException;
import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.dataprovider.UserReader;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUser(UserCommand.UserUpdateRequest command){}

    private User getUser(String userId){
        return userReader.findUser(userId);
    }

    private void validPassword(UserCommand.PasswordUpdateRequest command, User user){
        if(!passwordEncoder.matches(user.getPassword(), command.currentPassword())){
            throw new RuntimeException("비밀번호 오류");
        }
    }

    @Override
    public void updatePassword(UserCommand.PasswordUpdateRequest command){
        validPassword(command);
    }


}
