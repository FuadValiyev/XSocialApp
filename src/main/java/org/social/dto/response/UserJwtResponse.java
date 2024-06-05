package org.social.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

public record UserJwtResponse(Long id,
                              String fullName,
                              String email) {
}
