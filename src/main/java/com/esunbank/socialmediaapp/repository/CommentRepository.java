package com.esunbank.socialmediaapp.repository;

import com.esunbank.socialmediaapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "CALL sp_add_comment(:userId, :postId, :content)", nativeQuery = true)
    Long addComment(
            @Param("userId") Long userId,
            @Param("postId") Long postId,
            @Param("content") String content);

    @Query(value = "CALL sp_get_comments_by_post(:postId)", nativeQuery = true)
    List<Comment> getCommentsByPostId(@Param("postId") Long postId);

    List<Comment> findByPostPostId(Long postId);
}