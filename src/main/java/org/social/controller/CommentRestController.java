package org.social.controller;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.CommentRequest;
import org.social.dto.response.CommentResponse;
import org.social.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/{postId}/comment")
    public List<CommentResponse> getAllCommentsByPostId(@PathVariable Long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @GetMapping("/{username}/comments")
    public List<CommentResponse> getAllCommentsByUsername(@PathVariable String username) {
        return commentService.getAllCommentsByUsername(username);
    }

    @PostMapping("/{username}/{postId}/comment")
    public CommentResponse createComment(@PathVariable String username,
                                         @PathVariable Long postId,
                                         @RequestBody CommentRequest commentRequest) {
        return commentService.createComment(username, postId, commentRequest);
    }

    @PutMapping("/{username}/{postId}/{commentId}")
    public CommentResponse updateComment(@PathVariable String username,
                                         @PathVariable Long postId,
                                         @PathVariable Long commentId,
                                         @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(username, postId, commentId, commentRequest);
    }

    @DeleteMapping("/{username}/{postId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String username,
                                                @PathVariable Long postId,
                                                @PathVariable Long commentId) {
        if (commentService.deleteComment(username, postId, commentId)) {
            return ResponseEntity.ok("Comment deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }
    }
}
