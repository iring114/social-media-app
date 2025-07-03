package com.esunbank.socialmediaapp.repository;

import com.esunbank.socialmediaapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "CALL sp_create_post(:userId, :content, :image)", nativeQuery = true)
    Long createPost(
            @Param("userId") Long userId,
            @Param("content") String content,
            @Param("image") String image);

    @Query(value = "CALL sp_get_all_posts()", nativeQuery = true)
    List<Post> getAllPosts();

    @Query(value = "CALL sp_update_post(:postId, :content, :image)", nativeQuery = true)
    Integer updatePost(
            @Param("postId") Long postId,
            @Param("content") String content,
            @Param("image") String image);

    @Query(value = "CALL sp_delete_post(:postId)", nativeQuery = true)
    Integer deletePost(@Param("postId") Long postId);

    List<Post> findByUserUserId(Long userId);
}