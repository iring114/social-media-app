package com.esunbank.socialmediaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostRequest {

        @NotBlank(message = "發文內容不能為空")
        @Size(max = 1000, message = "發文內容不能超過1000個字符")
        private String content;

        private String image;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResponse {

        private Long postId;
        private Long userId;
        private String userName;
        private String content;
        private String image;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer commentCount;
    }
}