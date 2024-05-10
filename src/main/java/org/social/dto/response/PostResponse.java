package org.social.dto.response;


import org.apache.coyote.Response;
import org.social.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(Long postId,
                           String username,
                           String postText,
                           LocalDateTime postDate) {

    public static PostResponse convertPostToPostResponse(Post post) {
        return new PostResponse(post.getPostId(), post.getUser().getUsername(), post.getPostText(), post.getPostDate());
    }
}
