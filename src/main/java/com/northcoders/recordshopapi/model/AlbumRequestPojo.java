package com.northcoders.recordshopapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AlbumRequestPojo {
    private long id;
    private String albumName;
    private Genre genre;
    private Artist artist;
    private LocalDate publishedYear;
}
