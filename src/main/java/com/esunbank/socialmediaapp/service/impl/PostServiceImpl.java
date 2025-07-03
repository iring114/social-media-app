package com.esunbank.socialmediaapp.service.impl;

import com.esunbank.socialmediaapp.dto.PostDTO.PostRequest;
import com.esunbank.socialmediaapp.dto.PostDTO.PostResponse;
import com.esunbank.socialmediaapp.model.Post;
import com.esunbank.socialmediaapp.model.User;
import com.esunbank.socialmediaapp.repository.CommentRepository;
import com.esunbank.socialmediaapp.repository.PostRepository;
import com.esunbank.socialmediaapp.repository.UserRepository;
import com.esunbank.socialmediaapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public PostResponse createPost(Long userId, PostRequest request) {
        // 檢查用戶是否存在
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("找不到ID為: " + userId + " 的用戶"));

        // 使用存儲過程創建發文
        Long postId = postRepository.createPost(userId, request.getContent(), request.getImage());

        // 構建發文實體
        Post post = Post.builder()
                .postId(postId)
                .user(user)
                .content(request.getContent())
                .image(request.getImage())
                .build();

        return convertToDTO(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        // 使用存儲過程獲取所有發文
        List<Post> posts = postRepository.getAllPosts();
        return posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostsByUserId(Long userId) {
        // 檢查用戶是否存在
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("找不到ID為: " + userId + " 的用戶");
        }

        // 獲取指定用戶的所有發文
        List<Post> posts = postRepository.findByUserUserId(userId);
        return posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("找不到ID為: " + postId + " 的發文"));
        return convertToDTO(post);
    }

    @Override
    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostRequest request) {
        // 檢查發文是否存在
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("找不到ID為: " + postId + " 的發文"));

        // 檢查是否為發文的擁有者
        if (!post.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("您沒有權限更新此發文");
        }

        // 使用存儲過程更新發文
        Integer updated = postRepository.updatePost(postId, request.getContent(), request.getImage());

        if (updated <= 0) {
            throw new RuntimeException("更新發文失敗");
        }

        // 更新發文實體
        post.setContent(request.getContent());
        post.setImage(request.getImage());

        return convertToDTO(post);
    }

    @Override
    @Transactional
    public boolean deletePost(Long userId, Long postId) {
        // 檢查發文是否存在
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("找不到ID為: " + postId + " 的發文"));

        // 檢查是否為發文的擁有者
        if (!post.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("您沒有權限刪除此發文");
        }

        // 使用存儲過程刪除發文及其相關留言
        Integer deleted = postRepository.deletePost(postId);

        return deleted > 0;
    }

    @Override
    public PostResponse convertToDTO(Post post) {
        // 獲取發文的留言數量
        int commentCount = commentRepository.findByPostPostId(post.getPostId()).size();

        return PostResponse.builder()
                .postId(post.getPostId())
                .userId(post.getUser().getUserId())
                .userName(post.getUser().getUserName())
                .content(post.getContent())
                .image(post.getImage())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .commentCount(commentCount)
                .build();
    }
}