package com.esunbank.socialmediaapp.service;

import com.esunbank.socialmediaapp.dto.CommentDTO.CommentRequest;
import com.esunbank.socialmediaapp.dto.CommentDTO.CommentResponse;
import com.esunbank.socialmediaapp.model.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 添加留言
     *
     * @param userId  用戶ID
     * @param request 留言請求
     * @return 留言響應
     */
    CommentResponse addComment(Long userId, CommentRequest request);

    /**
     * 獲取指定發文的所有留言
     *
     * @param postId 發文ID
     * @return 留言列表
     */
    List<CommentResponse> getCommentsByPostId(Long postId);

    /**
     * 將留言實體轉換為DTO
     *
     * @param comment 留言實體
     * @return 留言響應DTO
     */
    CommentResponse convertToDTO(Comment comment);
}