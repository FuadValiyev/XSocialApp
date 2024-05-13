package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.response.LikeCommentResponse;
import org.social.dto.response.LikePostResponse;
import org.social.entities.Comment;
import org.social.entities.Like;
import org.social.entities.Post;
import org.social.entities.User;
import org.social.repository.CommentRepository;
import org.social.repository.LikeRepository;
import org.social.repository.PostRepository;
import org.social.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    public final LikeRepository likeRepository;

    public final PostRepository postRepository;

    public final UserRepository userRepository;

    public final CommentRepository commentRepository;


    public List<LikePostResponse> getAllUserLikeByUsername(String username) {
        List<Like> likes = likeRepository.findByUserUsername(username);
        return likes.stream().map(LikePostResponse::convertLikeToLikeResponse).toList();
    }

    public List<LikePostResponse> getAllUserLikeByPostId(Long postId) {
        List<Like> likes = likeRepository.findByPostPostId(postId);
        return likes.stream().map(LikePostResponse::convertLikeToLikeResponse).toList();
    }

    public List<LikeCommentResponse> getAllUserLikeByCommentId(Long commentId) {
        List<Like> likes = likeRepository.findByCommentCommentId(commentId);
        return likes.stream().map(LikeCommentResponse::convertLikeToLikeResponse).toList();
    }

    @Transactional
    public LikePostResponse createLikeByPostId(String username, Long postId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(username + " not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(postId + " not found"));
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setLikeDate(LocalDateTime.now());
        likeRepository.save(like);
        return LikePostResponse.convertLikeToLikeResponse(like);
    }

    @Transactional
    public LikeCommentResponse createLikeByCommentId(String username, Long commentId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(username + " not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(commentId + " not found"));
        Like like = new Like();
        like.setUser(user);
        like.setComment(comment);
        like.setLikeDate(LocalDateTime.now());
        likeRepository.save(like);
        return LikeCommentResponse.convertLikeToLikeResponse(like);
    }

    @Transactional
    public Boolean deleteLikeByLikeId(String username, Long postId, Long likeId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(username + " not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(postId + " not found"));
        if (username.equals(user.getUsername()) && postId.equals(post.getPostId())) {
            likeRepository.deleteById(likeId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteLikeByCommentId(String username, Long commentId, Long likeId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(username + " not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(commentId + " not found"));
        if (username.equals(user.getUsername()) && commentId.equals(comment.getCommentId())) {
            likeRepository.deleteById(likeId);
            return true;
        }
        return false;
    }
}
