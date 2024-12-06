package com.northcoders.recordshopapi.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ArtistPojo {

    @PositiveOrZero(message = "ID must be a positive number")
    private Long id;

    @NotNull(message = "Artist name cannot be null")
    @Size(min = 1, max = 255, message = "Artist name must be between 1 and 255 characters")
    @Pattern(regexp = "[a-zA-Z0-9- ]+", message = "Album name must not contain any special characters")
    private String artistName;

    public ArtistPojo() {
    }

    public ArtistPojo(Builder builder) {
        this.id = builder.id;
        this.artistName = builder.artistName;
    }
    public static class Builder {
        private Long id;
        private String artistName;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setArtistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public ArtistPojo build() {
            return new ArtistPojo(this);
        }
    }
}
