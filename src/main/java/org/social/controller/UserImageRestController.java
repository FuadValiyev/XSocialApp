package org.social.controller;

import lombok.RequiredArgsConstructor;
import org.social.dto.response.UserImageResponse;
import org.social.services.UserImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/userImages")
public class UserImageRestController {

    private final UserImageService userImageService;

    @PostMapping("/upload")
    public ResponseEntity<UserImageResponse> upload (@RequestParam("image")MultipartFile file,
                                                     @RequestParam Long userId) throws IOException {
       UserImageResponse response = userImageService.upload(file, userId);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{userId}")
    public ResponseEntity<byte[]> download(@PathVariable Long userId){
        byte[] image = userImageService.download(userId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

}

