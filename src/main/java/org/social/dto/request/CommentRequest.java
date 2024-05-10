package org.social.dto.request;

import org.social.entities.Comment;

import java.time.LocalDateTime;

public record CommentRequest(String commentText) {

    public static Comment covertCommentToCommentRequest(CommentRequest commentRequest){
        Comment comment = new Comment();
        comment.setCommentText(commentRequest.commentText);
        comment.setCommentDate(LocalDateTime.now());
        return comment;
    }
}
