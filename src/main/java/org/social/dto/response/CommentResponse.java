package org.social.dto.response;

import org.social.entities.Comment;

import java.time.LocalDateTime;

public record CommentResponse(String username,
                              Long postId,
                              String commentText,
                              LocalDateTime postdate) {

    public static CommentResponse convertCommentToCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getUser().getUsername(),
                comment.getPost().getPostId(),
                comment.getCommentText(),
                comment.getCommentDate());
    }
}
