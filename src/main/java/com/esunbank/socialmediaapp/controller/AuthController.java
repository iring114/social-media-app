package com.esunbank.socialmediaapp.controller;

import com.esunbank.socialmediaapp.dto.ApiResponse;
import com.esunbank.socialmediaapp.dto.AuthDTO.AuthResponse;
import com.esunbank.socialmediaapp.dto.AuthDTO.LoginRequest;
import com.esunbank.socialmediaapp.dto.AuthDTO.RegisterRequest;
import com.esunbank.socialmediaapp.dto.UserDTO;
import com.esunbank.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用戶註冊
     *
     * @param request 註冊請求
     * @return 註冊響應
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            UserDTO userDTO = userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("用戶註冊成功", userDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("註冊過程中發生錯誤: " + e.getMessage()));
        }
    }

    /**
     * 用戶登入
     *
     * @param request 登入請求
     * @return 登入響應
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            // 獲取JWT令牌
            String token = userService.login(request);
            
            // 獲取用戶信息
            UserDTO userDTO = userService.convertToDTO(userService.getUserByPhoneNumber(request.getPhoneNumber()));
            
            // 構建認證響應
            AuthResponse authResponse = new AuthResponse(token, userDTO);
            
            return ResponseEntity.ok(ApiResponse.success("登入成功", authResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("登入失敗: " + e.getMessage()));
        }
    }
}