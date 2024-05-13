package org.social.dto.response;

import org.social.entities.Like;

import java.time.LocalDateTime;

public record LikeCommentResponse(Long likeId,
                                  String likedUsername,
                                  String commentUsername,
                                  LocalDateTime likeDate) {

    public static LikeCommentResponse convertLikeToLikeResponse(Like like) {
        return new LikeCommentResponse(
                like.getLikeId(),
                like.getUser().getUsername(),
                like.getComment().getUser().getUsername(),
                like.getLikeDate()
        );
    }
}
