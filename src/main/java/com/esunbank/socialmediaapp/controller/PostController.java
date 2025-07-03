package com.esunbank.socialmediaapp.controller;

import com.esunbank.socialmediaapp.dto.ApiResponse;
import com.esunbank.socialmediaapp.dto.PostDTO.PostRequest;
import com.esunbank.socialmediaapp.dto.PostDTO.PostResponse;
import com.esunbank.socialmediaapp.model.User;
import com.esunbank.socialmediaapp.service.PostService;
import com.esunbank.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    /**
     * 創建新發文
     *
     * @param request 發文請求
     * @return 發文響應
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@Valid @RequestBody PostRequest request) {
        try {
            // 獲取當前登入用戶
            User currentUser = getCurrentUser();
            
            // 創建發文
            PostResponse postResponse = postService.createPost(currentUser.getUserId(), request);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("發文創建成功", postResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("發文創建失敗: " + e.getMessage()));
        }
    }

    /**
     * 獲取所有發文
     *
     * @return 發文列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts() {
        try {
            List<PostResponse> posts = postService.getAllPosts();
            return ResponseEntity.ok(ApiResponse.success("獲取所有發文成功", posts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("獲取所有發文失敗: " + e.getMessage()));
        }
    }

    /**
     * 獲取指定用戶的所有發文
     *
     * @param userId 用戶ID
     * @return 發文列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostsByUserId(@PathVariable Long userId) {
        try {
            List<PostResponse> posts = postService.getPostsByUserId(userId);
            return ResponseEntity.ok(ApiResponse.success("獲取用戶發文成功", posts));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("獲取用戶發文失敗: " + e.getMessage()));
        }
    }

    /**
     * 根據ID獲取發文
     *
     * @param postId 發文ID
     * @return 發文響應
     */
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPostById(@PathVariable Long postId) {
        try {
            PostResponse post = postService.getPostById(postId);
            return ResponseEntity.ok(ApiResponse.success("獲取發文成功", post));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("獲取發文失敗: " + e.getMessage()));
        }
    }

    /**
     * 更新發文
     *
     * @param postId  發文ID
     * @param request 發文請求
     * @return 更新後的發文響應
     */
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest request) {
        try {
            // 獲取當前登入用戶
            User currentUser = getCurrentUser();
            
            // 更新發文
            PostResponse updatedPost = postService.updatePost(currentUser.getUserId(), postId, request);
            
            return ResponseEntity.ok(ApiResponse.success("發文更新成功", updatedPost));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("發文更新失敗: " + e.getMessage()));
        }
    }

    /**
     * 刪除發文
     *
     * @param postId 發文ID
     * @return 刪除結果
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        try {
            // 獲取當前登入用戶
            User currentUser = getCurrentUser();
            
            // 刪除發文
            boolean deleted = postService.deletePost(currentUser.getUserId(), postId);
            
            if (deleted) {
                return ResponseEntity.ok(ApiResponse.success("發文刪除成功", null));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.error("發文刪除失敗"));
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("發文刪除失敗: " + e.getMessage()));
        }
    }

    /**
     * 獲取當前登入用戶
     *
     * @return 當前用戶
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();
        return userService.getUserByPhoneNumber(phoneNumber);
    }
}