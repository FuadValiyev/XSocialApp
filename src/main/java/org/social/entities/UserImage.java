package org.social.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "user_images")
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userImageId;

    private String name;

    private String type;

    @Column(length = 1000)
    private byte[] image;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
