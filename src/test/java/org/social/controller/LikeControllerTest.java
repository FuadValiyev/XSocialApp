package org.social.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.social.dto.response.LikeCommentResponse;
import org.social.dto.response.LikePostResponse;
import org.social.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LikeControllerTest {

    @Mock
    LikeService likeService;

    @InjectMocks
    LikeRestController likeRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUserLikeByUsername() {
        List<LikePostResponse> likes = new ArrayList<>();
        when(likeService.getAllUserLikeByUsername(anyString())).thenReturn(likes);

        List<LikePostResponse> result = likeRestController.getAllUserLikeByUsername("username");

        assertEquals(likes, result);
    }

    @Test
    void getAllUserLikeByPostId() {
        List<LikePostResponse> likes = new ArrayList<>();
        when(likeService.getAllUserLikeByPostId(anyLong())).thenReturn(likes);

        List<LikePostResponse> result = likeRestController.getAllUserLikeByPostId(1L);

        assertEquals(likes, result);
    }

    @Test
    void getAllUserLikeByCommentId() {
        List<LikeCommentResponse> likes = new ArrayList<>();
        when(likeService.getAllUserLikeByCommentId(anyLong())).thenReturn(likes);

        List<LikeCommentResponse> result = likeRestController.getAllUserLikeByCommentId(1L);

        assertEquals(likes, result);
    }

    @Test
    void createLikeByPostId() {
        LikePostResponse response = new LikePostResponse(1L, "username", "postedUsername", null);
        when(likeService.createLikeByPostId(anyString(), anyLong())).thenReturn(response);

        LikePostResponse result = likeRestController.createLikeByPostId("username", 1L);

        assertEquals(response, result);
    }

    @Test
    void createLikeByCommentId() {
        LikeCommentResponse response = new LikeCommentResponse(1L, "username", "commentUsername", null);
        when(likeService.createLikeByCommentId(anyString(), anyLong())).thenReturn(response);

        LikeCommentResponse result = likeRestController.createLikeByCommentId("username", 1L);

        assertEquals(response, result);
    }

    @Test
    void deleteLikeByLikeId() {
        when(likeService.deleteLikeByLikeId(anyString(), anyLong(), anyLong())).thenReturn(true);

        ResponseEntity<String> result = likeRestController.deleteLikeByLikeId("username", 1L, 1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Successfully deleted like", result.getBody());
    }

    @Test
    void deleteLikeByCommentId() {
        when(likeService.deleteLikeByCommentId(anyString(), anyLong(), anyLong())).thenReturn(true);

        ResponseEntity<String> result = likeRestController.deleteLikeByCommentId("username", 1L, 1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Successfully deleted like", result.getBody());
    }
}
