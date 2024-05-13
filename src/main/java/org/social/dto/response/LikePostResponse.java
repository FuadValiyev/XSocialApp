package org.social.dto.response;

import org.social.entities.Like;

import java.time.LocalDateTime;

public record LikePostResponse(Long likeId,
                               String likedUsername,
                               String postedUsername,
                               LocalDateTime likeDate) {

    public static LikePostResponse convertLikeToLikeResponse(Like like) {
        return new LikePostResponse(
                like.getLikeId(),
                like.getUser().getUsername(),
                like.getPost().getUser().getUsername(),
                like.getLikeDate());
    }
}

