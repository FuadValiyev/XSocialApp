package org.social.services;

import lombok.RequiredArgsConstructor;
import org.social.dto.response.PostImageResponse;
import org.social.entities.PostImage;
import org.social.mapper.PostImageMapper;
import org.social.repository.PostImageRepository;
import org.social.utilities.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostService postService;
    private final PostImageMapper postImageMapper;

    public PostImageResponse upload(MultipartFile file, Long postId) throws IOException {
        PostImage postImage = new PostImage();
        postImage.setName(file.getOriginalFilename());
        postImage.setType(file.getContentType());
        postImage.setImage(ImageUtil.compressImage(file.getBytes()));
        postImage.setPost(postService.getPostById(postId));
        postImageRepository.save(postImage);
        return postImageMapper.imageToResponse(postImage);
    }

    public byte[] download(Long postId){
        Optional<PostImage> postImage = postImageRepository.findPostImageByPost(postService.getPostById(postId));
        if (postImage.isPresent()) {
            return ImageUtil.decompressImage(postImage.get().getImage());
        }
        return null;
    }
}
