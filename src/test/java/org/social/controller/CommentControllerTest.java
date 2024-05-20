package org.social.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.social.dto.request.CommentRequest;
import org.social.dto.response.CommentResponse;
import org.social.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @Mock
    CommentService commentService;

    @InjectMocks
    CommentRestController commentRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCommentsByPostId() {
        List<CommentResponse> comments = new ArrayList<>();
        when(commentService.getAllCommentsByPostId(anyLong())).thenReturn(comments);

        List<CommentResponse> result = commentRestController.getAllCommentsByPostId(1L);

        assertEquals(comments, result);
    }

    @Test
    void getAllCommentsByUsername() {
        List<CommentResponse> comments = new ArrayList<>();
        when(commentService.getAllCommentsByUsername(anyString())).thenReturn(comments);

        List<CommentResponse> result = commentRestController.getAllCommentsByUsername("username");

        assertEquals(comments, result);
    }

    @Test
    void createComment() {
        CommentRequest request = new CommentRequest("test comment");
        CommentResponse response = new CommentResponse("username", 1L, "test comment", null);
        when(commentService.createComment(anyString(), anyLong(), any(CommentRequest.class))).thenReturn(response);

        CommentResponse result = commentRestController.createComment("username", 1L, request);

        assertEquals(response, result);
    }

    @Test
    void updateComment() {
        CommentRequest request = new CommentRequest("updated comment");
        CommentResponse response = new CommentResponse("username", 1L, "updated comment", null);
        when(commentService.updateComment(anyString(), anyLong(), anyLong(), any(CommentRequest.class))).thenReturn(response);

        CommentResponse result = commentRestController.updateComment("username", 1L, 1L, request);

        assertEquals(response, result);
    }

    @Test
    void deleteComment() {
        when(commentService.deleteComment(anyString(), anyLong(), anyLong())).thenReturn(true);

        ResponseEntity<String> result = commentRestController.deleteComment("username", 1L, 1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Comment deleted successfully", result.getBody());
    }
}
