package org.social.mapper;

import org.social.dto.response.PostImageResponse;
import org.social.entities.PostImage;
import org.springframework.stereotype.Component;

@Component
public class PostImageMapper {

    public static PostImageResponse imageToResponse(PostImage postImage) {
        if (postImage == null) {
            return null;
        }

        return new PostImageResponse(
                postImage.getPostImageId(),
                postImage.getName(),
                postImage.getType(),
                postImage.getImage(),
                postImage.getPost().getUser().getUserId()
        );
    }
}