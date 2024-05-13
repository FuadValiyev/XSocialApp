package org.social.controller;

import lombok.RequiredArgsConstructor;
import org.social.dto.response.LikeCommentResponse;
import org.social.dto.response.LikePostResponse;
import org.social.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("app/likes")
public class LikeRestController {

    private final LikeService likeService;

    @GetMapping("/user/{username}")
    public List<LikePostResponse> getAllUserLikeByUsername(@PathVariable String username) {
        return likeService.getAllUserLikeByUsername(username);
    }

    @GetMapping("/post/{postId}")
    public List<LikePostResponse> getAllUserLikeByPostId(@PathVariable Long postId) {
        return likeService.getAllUserLikeByPostId(postId);
    }

    @GetMapping("/comment/{commentId}")
    public List<LikeCommentResponse> getAllUserLikeByCommentId(@PathVariable Long commentId) {
        return likeService.getAllUserLikeByCommentId(commentId);
    }

    @PostMapping("/post/{username}/{postId}")
    public LikePostResponse createLikeByPostId(@PathVariable String username, @PathVariable Long postId) {
        return likeService.createLikeByPostId(username, postId);
    }

    @PostMapping("/comment/{username}/{commentId}")
    public LikeCommentResponse createLikeByCommentId(@PathVariable String username, @PathVariable Long commentId) {
        return likeService.createLikeByCommentId(username, commentId);
    }

    @DeleteMapping("/psot/{username}/{postId}/{likeId}")
    public ResponseEntity<String> deleteLikeByLikeId(@PathVariable String username, @PathVariable Long postId, @PathVariable Long likeId) {
        if (likeService.deleteLikeByLikeId(username, postId, likeId)) {
            return ResponseEntity.ok("Successfully deleted like");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete like");
        }
    }

    @DeleteMapping("comment/{username}/{commentId}/{likeId}")
    public ResponseEntity<String> deleteLikeByCommentId(@PathVariable String username, @PathVariable Long commentId, @PathVariable Long likeId) {
        if (likeService.deleteLikeByCommentId(username, commentId, likeId)) {
            return ResponseEntity.ok("Successfully deleted like");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete like");
        }
    }
}
