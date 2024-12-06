package com.northcoders.recordshopapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false)
    private String albumName;

    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;

    public Album() {
    }

    public static AlbumBuilder builder() {
        return new AlbumBuilder();
    }

    public static class AlbumBuilder {
        private long id;
        private String albumName;
        private LocalDate releaseDate;
        private Genre genre;
        private Artist artist;
        private LocalDateTime createdDt;
        private LocalDateTime modifiedDt;

        AlbumBuilder() {
        }

        public AlbumBuilder id(long id) {
            this.id = id;
            return this;
        }

        public AlbumBuilder albumName(String albumName) {
            this.albumName = albumName;
            return this;
        }

        public AlbumBuilder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public AlbumBuilder genre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public AlbumBuilder artist(Artist artist) {
            this.artist = artist;
            return this;
        }

        public AlbumBuilder createdDt(LocalDateTime createdDt) {
            this.createdDt = createdDt;
            return this;
        }

        public AlbumBuilder modifiedDt(LocalDateTime modifiedDt) {
            this.modifiedDt = modifiedDt;
            return this;
        }

        public Album build() {
            return new Album(this.id, this.albumName, this.releaseDate, this.genre, this.artist, this.createdDt, this.modifiedDt);
        }

        public String toString() {
            return "Album.AlbumBuilder(id=" + this.id + ", albumName=" + this.albumName + ", releaseDate=" + this.releaseDate + ", genre=" + this.genre + ", artist=" + this.artist + ", createdDt=" + this.createdDt + ", modifiedDt=" + this.modifiedDt + ")";
        }
    }


}
