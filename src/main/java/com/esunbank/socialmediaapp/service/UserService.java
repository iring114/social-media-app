package com.esunbank.socialmediaapp.service;

import com.esunbank.socialmediaapp.dto.AuthDTO.LoginRequest;
import com.esunbank.socialmediaapp.dto.AuthDTO.RegisterRequest;
import com.esunbank.socialmediaapp.dto.UserDTO;
import com.esunbank.socialmediaapp.model.User;

public interface UserService {

    /**
     * 註冊新用戶
     *
     * @param request 註冊請求
     * @return 註冊成功的用戶信息
     */
    UserDTO register(RegisterRequest request);

    /**
     * 用戶登入
     *
     * @param request 登入請求
     * @return JWT令牌
     */
    String login(LoginRequest request);

    /**
     * 根據手機號碼獲取用戶
     *
     * @param phoneNumber 手機號碼
     * @return 用戶實體
     */
    User getUserByPhoneNumber(String phoneNumber);

    /**
     * 根據用戶ID獲取用戶
     *
     * @param userId 用戶ID
     * @return 用戶DTO
     */
    UserDTO getUserById(Long userId);

    /**
     * 將用戶實體轉換為DTO
     *
     * @param user 用戶實體
     * @return 用戶DTO
     */
    UserDTO convertToDTO(User user);
}