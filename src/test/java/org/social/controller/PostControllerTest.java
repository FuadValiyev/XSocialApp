package org.social.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.social.dto.request.PostRequest;
import org.social.dto.response.PostResponse;
import org.social.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    PostService postService;

    @InjectMocks
    PostRestController postRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPosts() {
        List<PostResponse> posts = new ArrayList<>();
        when(postService.getAllPost()).thenReturn(posts);

        List<PostResponse> result = postRestController.getAllPosts();

        assertEquals(posts, result);
    }

    @Test
    void getAllPostByUsername() {
        List<PostResponse> posts = new ArrayList<>();
        when(postService.getAllPostByUser(anyString())).thenReturn(posts);

        List<PostResponse> result = postRestController.getAllPostByUsername("username");

        assertEquals(posts, result);
    }

    @Test
    void createPostByUser() {
        PostRequest request = new PostRequest("post text");
        PostResponse response = new PostResponse(1L, "username", "post text", null);
        when(postService.createPostByUser(anyString(), any())).thenReturn(response);

        PostResponse result = postRestController.createPostByUser("username", request);

        assertEquals(response, result);
    }

//    @Test
//    void updatePost() {
//        PostRequest request = new PostRequest("updated post text");
//        ResponseEntity<Object> response = ResponseEntity.ok().build();
//        when(postService.updatePost(anyLong(), any())).thenReturn(response);
//
//        ResponseEntity<Object> result = postRestController.updatePost(1L, request);
//
//        assertEquals(response, result);
//    }

    @Test
    void deletePost() {
        when(postService.deletePostByPostId(anyLong())).thenReturn(true);

        ResponseEntity<String> result = postRestController.deletePost(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Post deleted successfully", result.getBody());
    }
}
