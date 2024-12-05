package com.northcoders.recordshopapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artistName;

    @OneToMany(mappedBy = "artist",cascade =CascadeType.ALL)
    private List<Album> albums;
    public Artist() {
    }
}
