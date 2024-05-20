package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.PostRequest;
import org.social.dto.response.PostResponse;
import org.social.entities.Post;
import org.social.entities.User;
import org.social.repository.PostRepository;
import org.social.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.social.Utilities.Utility.IllegalArgException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public List<PostResponse> getAllPost() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "postDate"));
        return posts.stream().map(PostResponse::convertPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getAllPostByUser(String username) {
        User foundUser = findUserByUsername(username);
        List<Post> posts = postRepository.findPostByUser(foundUser);
        return posts.stream().map(PostResponse::convertPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse createPostByUser(String username, PostRequest postRequest) {
        User foundUser = findUserByUsername(username);
        Post post = PostRequest.convertPostRequestToPost(postRequest);
        post.setUser(foundUser);
        Post savedpost = postRepository.save(post);
        return PostResponse.convertPostToPostResponse(savedpost);
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
        Optional.ofNullable(postRequest.postText()).ifPresent(post::setPostText);
        Post savedPost = postRepository.save(post);
        return PostResponse.convertPostToPostResponse(savedPost);
    }

    public Boolean deletePostByPostId(Long postId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> IllegalArgException(postId));
            postRepository.delete(post);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}