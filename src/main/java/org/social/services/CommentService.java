package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.CommentRequest;
import org.social.dto.response.CommentResponse;
import org.social.entities.Comment;
import org.social.entities.Post;
import org.social.entities.User;
import org.social.repository.CommentRepository;
import org.social.repository.PostRepository;
import org.social.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.social.Utilities.Utility.IllegalArgException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> getAllCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream().map(CommentResponse::convertCommentToCommentResponse).toList();
    }

    public List<CommentResponse> getAllCommentsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
        List<Comment> comments = commentRepository.findByUser(user);
        return comments.stream().map(CommentResponse::convertCommentToCommentResponse).toList();
    }

    @Transactional
    public CommentResponse createComment(String username, Long postId, CommentRequest commentRequest) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
        Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
        Comment comment = CommentRequest.covertCommentToCommentRequest(commentRequest);
        comment.setUser(user);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.convertCommentToCommentResponse(savedComment);
    }

    @Transactional
    public CommentResponse updateComment(String username, Long postId, Long commentId, CommentRequest commentRequest) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
        Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> IllegalArgException(commentId));
        if (comment.getUser().equals(user) && comment.getPost().equals(post)) {
            Optional.ofNullable(commentRequest.commentText()).ifPresent(comment::setCommentText);
            Comment savedComment = commentRepository.save(comment);
            return CommentResponse.convertCommentToCommentResponse(savedComment);
        }
        return null;
    }

    @Transactional
    public boolean deleteComment(String username, Long postId, Long commentId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
        Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> IllegalArgException(commentId));
        if (comment.getUser().equals(user) && comment.getPost().equals(post)) {
            commentRepository.delete(comment);
            return true;
        } else {
            return false;
        }
    }
}
