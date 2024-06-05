package org.social.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "post_images")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageId;

    private String name;

    private String type;

    @Column(length = 1000)
    private byte[] image;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
