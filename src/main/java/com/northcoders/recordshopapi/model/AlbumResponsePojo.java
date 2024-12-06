package com.northcoders.recordshopapi.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
@Data
public class AlbumResponsePojo {
    private long id;
    private String albumName;
    private Genre genre;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private ArtistPojo artist;
    private AlbumResponsePojo(Builder builder) {
        this.id = builder.id;
        this.albumName = builder.albumName;
        this.genre = builder.genre;
        this.releaseDate = builder.releaseDate;
        this.artist = builder.artist;
    }
    public static class Builder {
        private long id;
        private String albumName;
        private Genre genre;
        private LocalDate releaseDate;
        private ArtistPojo artist;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setAlbumName(String albumName) {
            this.albumName = albumName;
            return this;
        }

        public Builder setGenre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public Builder setReleaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setArtist(ArtistPojo artist) {
            this.artist = artist;
            return this;
        }

        public AlbumResponsePojo build() {
            return new AlbumResponsePojo(this);
        }
    }

}
