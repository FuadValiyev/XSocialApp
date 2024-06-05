package org.social.repository;

import org.social.entities.Like;
import org.social.entities.Post;
import org.social.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByUserUsername(String username);

    List<Like> findByPostPostId(Long postId);

    List<Like> findByCommentCommentId(Long commentId);

    Optional<Like> findByUserAndPost(User user, Post post);
}