package com.esunbank.socialmediaapp.controller;

import com.esunbank.socialmediaapp.dto.ApiResponse;
import com.esunbank.socialmediaapp.dto.CommentDTO.CommentRequest;
import com.esunbank.socialmediaapp.dto.CommentDTO.CommentResponse;
import com.esunbank.socialmediaapp.model.User;
import com.esunbank.socialmediaapp.service.CommentService;
import com.esunbank.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    /**
     * 添加留言
     *
     * @param request 留言請求
     * @return 留言響應
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(@Valid @RequestBody CommentRequest request) {
        try {
            // 獲取當前登入用戶
            User currentUser = getCurrentUser();
            
            // 添加留言
            CommentResponse commentResponse = commentService.addComment(currentUser.getUserId(), request);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("留言添加成功", commentResponse));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("留言添加失敗: " + e.getMessage()));
        }
    }

    /**
     * 獲取指定發文的所有留言
     *
     * @param postId 發文ID
     * @return 留言列表
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(ApiResponse.success("獲取留言成功", comments));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("獲取留言失敗: " + e.getMessage()));
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