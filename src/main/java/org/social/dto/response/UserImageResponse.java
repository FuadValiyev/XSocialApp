package org.social.dto.response;

public record UserImageResponse(Long ImageId,
                                String name,
                                String type,
                                byte[] image,
                                Long userId) {
}
