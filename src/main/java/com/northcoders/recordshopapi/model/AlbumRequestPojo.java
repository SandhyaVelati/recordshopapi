package com.northcoders.recordshopapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlbumRequestPojo {
    @PositiveOrZero(message = "ID must be a positive number") //pattern doesn't work ono long
    private long id;

    @NotNull
    @Size(min = 1, max = 255, message = "Album name must be between 1 and 350 characters")
    @Pattern(regexp = "[a-zA-Z0-9- ]+", message = "Album name must not contain any special characters")
    private String albumName;

    @NotNull(message = "Genre cannot be null")
    private Genre genre;

    @PastOrPresent(message = "Album release date must be in the past or present, format: yyyy-MM-dd ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format yyyy-MM-dd") doesn't work here too, String to Date parsing try
    private LocalDate releaseDate;

    @Valid
    @NotNull(message = "Artist details are required")
    private ArtistPojo artist;

    public AlbumRequestPojo(long id, String albumName, Genre genre, LocalDate releaseDate, ArtistPojo artist) {
        this.id = id;
        this.albumName = albumName;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.artist = artist;
    }
}
