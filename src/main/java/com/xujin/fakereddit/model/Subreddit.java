package com.xujin.fakereddit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
    @Id@GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Community name is required")
    private String name;

    @NotBlank(message = "Discription is required")
    private String sescription;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts ;
    private Instant createDate;

    @ManyToMany(fetch = FetchType.LAZY)
    private User  user;
}
