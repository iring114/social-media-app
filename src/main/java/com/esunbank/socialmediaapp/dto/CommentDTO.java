package com.esunbank.socialmediaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CommentDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentRequest {

        @NotNull(message = "發文ID不能為空")
        private Long postId;

        @NotBlank(message = "留言內容不能為空")
        @Size(max = 500, message = "留言內容不能超過500個字符")
        private String content;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponse {

        private Long commentId;
        private Long userId;
        private String userName;
        private Long postId;
        private String content;
        private LocalDateTime createdAt;
    }
}