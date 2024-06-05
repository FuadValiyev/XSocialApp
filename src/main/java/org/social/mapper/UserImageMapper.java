package org.social.mapper;

import org.social.dto.response.UserImageResponse;
import org.social.entities.UserImage;
import org.springframework.stereotype.Component;

@Component
public class UserImageMapper {

    public static UserImageResponse imageToResponse(UserImage userImage) {
        if (userImage == null) {
            return null;
        }

        return new UserImageResponse(
                userImage.getUserImageId(),
                userImage.getName(),
                userImage.getType(),
                userImage.getImage(),
                userImage.getUser().getUserId()
        );
    }
}
