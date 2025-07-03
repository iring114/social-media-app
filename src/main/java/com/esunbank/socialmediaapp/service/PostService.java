package com.esunbank.socialmediaapp.service;

import com.esunbank.socialmediaapp.dto.PostDTO.PostRequest;
import com.esunbank.socialmediaapp.dto.PostDTO.PostResponse;
import com.esunbank.socialmediaapp.model.Post;

import java.util.List;

public interface PostService {

    /**
     * 創建新發文
     *
     * @param userId  用戶ID
     * @param request 發文請求
     * @return 發文響應
     */
    PostResponse createPost(Long userId, PostRequest request);

    /**
     * 獲取所有發文
     *
     * @return 發文列表
     */
    List<PostResponse> getAllPosts();

    /**
     * 獲取指定用戶的所有發文
     *
     * @param userId 用戶ID
     * @return 發文列表
     */
    List<PostResponse> getPostsByUserId(Long userId);

    /**
     * 根據ID獲取發文
     *
     * @param postId 發文ID
     * @return 發文響應
     */
    PostResponse getPostById(Long postId);

    /**
     * 更新發文
     *
     * @param userId  用戶ID
     * @param postId  發文ID
     * @param request 發文請求
     * @return 更新後的發文響應
     */
    PostResponse updatePost(Long userId, Long postId, PostRequest request);

    /**
     * 刪除發文
     *
     * @param userId 用戶ID
     * @param postId 發文ID
     * @return 是否刪除成功
     */
    boolean deletePost(Long userId, Long postId);

    /**
     * 將發文實體轉換為DTO
     *
     * @param post 發文實體
     * @return 發文響應DTO
     */
    PostResponse convertToDTO(Post post);
}