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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.social.utilities.ExceptionUtil.IllegalArgException;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;


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
        User user = userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
        Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setLikeDate(LocalDateTime.now());
        likeRepository.save(like);
        return LikePostResponse.convertLikeToLikeResponse(like);
    }

    @Transactional
    public LikeCommentResponse createLikeByCommentId(String username, Long commentId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> IllegalArgException(commentId));
        Like like = new Like();
        like.setUser(user);
        like.setComment(comment);
        like.setLikeDate(LocalDateTime.now());
        likeRepository.save(like);
        return LikeCommentResponse.convertLikeToLikeResponse(like);
    }

    @Transactional
    public Boolean deleteLikeByLikeId(String username, Long postId, Long likeId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
        Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
        if (username.equals(user.getUsername()) && postId.equals(post.getPostId())) {
            likeRepository.deleteById(likeId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteLikeByCommentId(String username, Long commentId, Long likeId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> IllegalArgException(commentId));
        if (username.equals(user.getUsername()) && commentId.equals(comment.getCommentId())) {
            likeRepository.deleteById(likeId);
            return true;
        }
        return false;
    }

    public boolean isLiked(Long userId,Long postId){
        User user = userRepository.findById(userId).orElseThrow(() -> IllegalArgException(userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
        Optional<Like> like = likeRepository.findByUserAndPost(user,post);
        return like.isPresent();
    }
}
