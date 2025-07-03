package com.esunbank.socialmediaapp.service.impl;

import com.esunbank.socialmediaapp.dto.CommentDTO.CommentRequest;
import com.esunbank.socialmediaapp.dto.CommentDTO.CommentResponse;
import com.esunbank.socialmediaapp.model.Comment;
import com.esunbank.socialmediaapp.model.Post;
import com.esunbank.socialmediaapp.model.User;
import com.esunbank.socialmediaapp.repository.CommentRepository;
import com.esunbank.socialmediaapp.repository.PostRepository;
import com.esunbank.socialmediaapp.repository.UserRepository;
import com.esunbank.socialmediaapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public CommentResponse addComment(Long userId, CommentRequest request) {
        // 檢查用戶是否存在
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("找不到ID為: " + userId + " 的用戶"));

        // 檢查發文是否存在
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("找不到ID為: " + request.getPostId() + " 的發文"));

        // 使用存儲過程添加留言
        Long commentId = commentRepository.addComment(userId, request.getPostId(), request.getContent());

        // 構建留言實體
        Comment comment = Comment.builder()
                .commentId(commentId)
                .user(user)
                .post(post)
                .content(request.getContent())
                .build();

        return convertToDTO(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        // 檢查發文是否存在
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("找不到ID為: " + postId + " 的發文");
        }

        // 使用存儲過程獲取指定發文的所有留言
        List<Comment> comments = commentRepository.getCommentsByPostId(postId);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse convertToDTO(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId())
                .userName(comment.getUser().getUserName())
                .postId(comment.getPost().getPostId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}