package org.social.services;

import lombok.RequiredArgsConstructor;
import org.social.dto.response.UserImageResponse;
import org.social.entities.UserImage;
import org.social.mapper.UserImageMapper;
import org.social.repository.UserImageRepository;
import org.social.utilities.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final UserImageRepository userImageRepository;
    private final UserService userService;
    private final UserImageMapper userImageMapper;

    public UserImageResponse upload(MultipartFile file, Long userId) throws IOException {
        UserImage userImage = new UserImage();
        userImage.setImage(ImageUtil.compressImage(file.getBytes()));
        userImage.setName(file.getOriginalFilename());
        userImage.setType(file.getContentType());
        userImage.setUser(userService.getUserById(userId));
        userImageRepository.save(userImage);
        return userImageMapper.imageToResponse(userImage);
    }

    public byte[] download(Long id){
        Optional<UserImage> userImage = userImageRepository.findById(id);
        return ImageUtil.decompressImage(userImage.get().getImage());
    }
}
