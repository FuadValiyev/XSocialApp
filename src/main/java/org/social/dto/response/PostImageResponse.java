package org.social.dto.response;


public record PostImageResponse(Long ImageId,
                                String name,
                                String type,
                                byte[] image,
                                Long userId) {
}
