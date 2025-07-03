package com.esunbank.socialmediaapp.service.impl;

import com.esunbank.socialmediaapp.dto.AuthDTO.LoginRequest;
import com.esunbank.socialmediaapp.dto.AuthDTO.RegisterRequest;
import com.esunbank.socialmediaapp.dto.UserDTO;
import com.esunbank.socialmediaapp.model.User;
import com.esunbank.socialmediaapp.repository.UserRepository;
import com.esunbank.socialmediaapp.security.JwtUtils;
import com.esunbank.socialmediaapp.security.SecurityUtils;
import com.esunbank.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional
    public UserDTO register(RegisterRequest request) {
        // 檢查手機號碼是否已存在
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("該手機號碼已被註冊");
        }

        // 檢查電子郵件是否已存在
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("該電子郵件已被註冊");
        }

        // 生成鹽值
        String salt = securityUtils.generateSalt();
        // 使用鹽值對密碼進行雜湊
        String hashedPassword = securityUtils.hashPassword(request.getPassword(), salt);

        // 創建用戶實體
        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(hashedPassword)
                .salt(salt)
                .biography(request.getBiography())
                .build();

        // 使用存儲過程註冊用戶
        Long userId = userRepository.registerUser(
                user.getUserName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getSalt(),
                user.getBiography());

        user.setUserId(userId);

        return convertToDTO(user);
    }

    @Override
    public String login(LoginRequest request) {
        // 根據手機號碼獲取用戶
        User user = getUserByPhoneNumber(request.getPhoneNumber());

        // 驗證密碼
        if (!securityUtils.verifyPassword(request.getPassword(), user.getSalt(), user.getPassword())) {
            throw new BadCredentialsException("密碼不正確");
        }

        // 生成JWT令牌
        return jwtUtils.generateToken(user.getPhoneNumber());
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("找不到手機號碼為: " + phoneNumber + " 的用戶"));
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("找不到ID為: " + userId + " 的用戶"));
        return convertToDTO(user);
    }

    @Override
    public UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .coverImage(user.getCoverImage())
                .biography(user.getBiography())
                .build();
    }
}