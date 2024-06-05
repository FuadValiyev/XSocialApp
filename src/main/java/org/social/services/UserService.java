package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.UserRequest;
import org.social.dto.response.UserResponse;
import org.social.entities.Follow;
import org.social.entities.User;
import org.social.repository.FollowRepository;
import org.social.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.social.utilities.ExceptionUtil.IllegalArgException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = UserRequest.convertUserRequestToUser(userRequest);
        return UserResponse.convertUserToUserResponse(userRepository.save(user));
    }

    @Transactional
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponse::convertUserToUserResponse).collect(Collectors.toList());
    }

    public UserResponse getUserByUsername(String username) {
        User foundUser = findUserByUsername(username);
        return UserResponse.convertUserToUserResponse(foundUser);
    }

    @Transactional
    public UserResponse updateUserByUsername(String username, UserRequest requestBody) {
        User foundUser = findUserByUsername(username);
        User reqestUser = UserRequest.convertUserRequestToUser(requestBody);
        User user = userRepository.save(updateUser(foundUser, reqestUser));
        return UserResponse.convertUserToUserResponse(user);
    }

    @Transactional
    public Boolean isFollowing(Long userId, Long followId) {
        User user = userRepository.findById(userId).orElseThrow(()-> IllegalArgException(userId));
        Follow follower = followRepository.findById(followId).orElseThrow(()-> IllegalArgException(followId));
        Optional<Follow> follow = followRepository.findByUserAndUserFollow(user, follower);
        return follow.isPresent();
    }

    public Boolean deleteUserByUsername(String username) {
        try {
            User foundUser = findUserByUsername(username);
            userRepository.delete(foundUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    protected User updateUser(User foundUser, User requestUser) {
        Optional.ofNullable(requestUser.getName()).ifPresent(foundUser::setName);
        Optional.ofNullable(requestUser.getSurname()).ifPresent(foundUser::setSurname);
        Optional.ofNullable(requestUser.getBirthday()).ifPresent(foundUser::setBirthday);
        return foundUser;
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> IllegalArgException(username));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }
}
