package org.social.controller;

import lombok.RequiredArgsConstructor;
import org.social.dto.response.PostImageResponse;
import org.social.services.PostImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postImages")
public class PostImagesRestController {

    private final PostImageService postImageService;

    @PostMapping("/upload")
    public ResponseEntity<PostImageResponse> upload(@RequestParam("image") MultipartFile file,
                                                    @RequestParam Long postId) throws IOException {
        PostImageResponse postImageResponse = postImageService.upload(file, postId);
        return new ResponseEntity<>(postImageResponse, HttpStatus.OK);
    }

    @GetMapping("/download/{postId}")
    public ResponseEntity<?> download(@PathVariable Long postId) {
        byte[] image = postImageService.download(postId);
        if (image != null) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
