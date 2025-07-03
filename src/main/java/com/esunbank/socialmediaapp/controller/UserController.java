package com.esunbank.socialmediaapp.controller;

import com.esunbank.socialmediaapp.dto.ApiResponse;
import com.esunbank.socialmediaapp.dto.UserDTO;
import com.esunbank.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 獲取當前登入用戶信息
     *
     * @return 用戶信息
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser() {
        try {
            // 從安全上下文中獲取當前用戶的手機號碼
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String phoneNumber = authentication.getName();
            
            // 獲取用戶信息
            UserDTO userDTO = userService.convertToDTO(userService.getUserByPhoneNumber(phoneNumber));
            
            return ResponseEntity.ok(ApiResponse.success("獲取當前用戶信息成功", userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("獲取當前用戶信息失敗: " + e.getMessage()));
        }
    }

    /**
     * 根據用戶ID獲取用戶信息
     *
     * @param userId 用戶ID
     * @return 用戶信息
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long userId) {
        try {
            UserDTO userDTO = userService.getUserById(userId);
            return ResponseEntity.ok(ApiResponse.success("獲取用戶信息成功", userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("獲取用戶信息失敗: " + e.getMessage()));
        }
    }
}