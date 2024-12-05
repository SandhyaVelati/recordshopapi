package com.northcoders.recordshopapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String albumName;

    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;
    public Album(){}

    public Album(long id, String albumName, LocalDate releaseDate,
                 Genre genre, Artist artist, LocalDateTime createdDt, LocalDateTime modifiedDt) {
        this.id = id;
        this.albumName = albumName;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.artist = artist;
        this.createdDt = createdDt;
        this.modifiedDt = modifiedDt;
    }
}
