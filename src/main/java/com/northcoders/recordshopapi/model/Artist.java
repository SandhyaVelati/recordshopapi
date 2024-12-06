package com.northcoders.recordshopapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String artistName;

    @OneToMany(mappedBy = "artist",cascade =CascadeType.ALL)
    private List<Album> albums;
    public Artist() {}
}
