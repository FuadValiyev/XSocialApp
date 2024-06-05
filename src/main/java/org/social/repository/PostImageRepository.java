package org.social.repository;

import org.social.entities.Post;
import org.social.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    Optional<PostImage> findPostImageByPost(Post post);
}
