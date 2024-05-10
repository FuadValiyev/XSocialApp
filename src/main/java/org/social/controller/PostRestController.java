package org.social.controller;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.PostRequest;
import org.social.dto.response.PostResponse;
import org.social.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostRestController {

    private final PostService postService;

    @GetMapping("/all")
    public List<PostResponse> getAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping("/{username}/all")
    public List<PostResponse> getAllPostByUsername(@PathVariable String username){
        return postService.getAllPostByUser(username);
    }

    @PostMapping("/{username}/creat")
    public PostResponse createPostByUser(@PathVariable String username, @RequestBody PostRequest postRequest){
        return postService.createPostByUser(username, postRequest);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.updatePost(postId, postRequest));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        if( postService.deletePostByPostId(postId)){
            return ResponseEntity.ok("Post deleted successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }
}
