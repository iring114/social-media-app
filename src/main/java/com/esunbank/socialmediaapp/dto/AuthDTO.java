package com.esunbank.socialmediaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {

        @NotBlank(message = "用戶名不能為空")
        @Size(min = 2, max = 50, message = "用戶名長度必須在2-50個字符之間")
        private String userName;

        @NotBlank(message = "電子郵件不能為空")
        @Email(message = "電子郵件格式不正確")
        private String email;

        @NotBlank(message = "手機號碼不能為空")
        @Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式不正確，應為09開頭的10位數字")
        private String phoneNumber;

        @NotBlank(message = "密碼不能為空")
        @Size(min = 8, message = "密碼長度不能少於8個字符")
        private String password;

        private String biography;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {

        @NotBlank(message = "手機號碼不能為空")
        @Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式不正確，應為09開頭的10位數字")
        private String phoneNumber;

        @NotBlank(message = "密碼不能為空")
        private String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {

        private String token;
        private UserDTO user;
    }
}