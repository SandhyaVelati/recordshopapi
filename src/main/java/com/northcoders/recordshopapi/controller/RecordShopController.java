package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.Exception.InvalidInputArgument;
import com.northcoders.recordshopapi.model.*;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class RecordShopController {
    @Autowired
    private RecordShopServiceImpl recordShopService;
    @PostMapping("/album")
    public ResponseEntity<AlbumResponsePojo> createAlbum(@Valid @RequestBody AlbumRequestPojo album) {
        AlbumResponsePojo createdAlbum = recordShopService.createAlbum(album);
        return new ResponseEntity<>(createdAlbum, HttpStatus.CREATED);
    }
    @GetMapping("/albums")
    public ResponseEntity<List<AlbumResponsePojo>> getAllAlbums() {
        return new ResponseEntity<>(recordShopService.getAlbums(),HttpStatus.OK);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Long id) {
         return new ResponseEntity<>(recordShopService.getAlbumById(id),HttpStatus.OK);
         //orElseThrow -> controller advice
    }

    @PutMapping("/album/{id}")
    public ResponseEntity<AlbumResponsePojo> updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumRequestUpdatePojo album){
         return new ResponseEntity<>(recordShopService.updateAlbum(id,album),HttpStatus.OK);
    }
    @DeleteMapping("/album/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long id){
        recordShopService.deleteAlbum(id);
        return new ResponseEntity<String>("deleted album successfully", HttpStatus.OK);
    }


    @GetMapping("/albums/artist/{name}")
    public ResponseEntity<?> getAlbumsByArtistId(@PathVariable String name) {
        if(!name.matches("[a-zA-Z0-9- ]+")){
            throw new InvalidInputArgument("Invalid characters encountered in Artist name");
            //return new ResponseEntity<>(new RuntimeException("Invalid characters encountered in Artist name"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(recordShopService.getAlbumByArtistName(name),HttpStatus.OK);
    }

    @GetMapping("/albums/genre/{genre}")
    public ResponseEntity<?> getAlbumsByGenre(@PathVariable String genre) {
        if(Arrays.stream(Genre.values()).noneMatch(genreInEnum -> genre.equalsIgnoreCase(genreInEnum.toString()))){
            throw new InvalidInputArgument("Invalid Genre encountered in input");
        }
        return new ResponseEntity<>(recordShopService.getAlbumByGenre(genre),HttpStatus.OK);
    }
    @GetMapping("/albums/genre/year/{year}")
    public ResponseEntity<?> getAlbumsByYear(@PathVariable Integer year){
        if(year == null || year < 0 || year > LocalDate.now().getYear()){
            throw new InvalidInputArgument("Invalid year, or future Date requested");
        }
        if(!year.toString().matches("\\d{4}")){
            throw new InvalidInputArgument("please provide a valid year in the format YYYY");
        }
        return new ResponseEntity<>(recordShopService.getAlbumByReleasedDate(year),HttpStatus.OK);
    }

    @GetMapping("/albums/name/{albumName}")
    public ResponseEntity<?> getAlbumsByYear(@PathVariable String albumName){
        if(!albumName.matches("[a-zA-Z0-9- ]+")){
            throw new InvalidInputArgument("Invalid characters encountered in Album name");
        }
        return new ResponseEntity<>(recordShopService.getAlbumByName(albumName),HttpStatus.OK);
    }

}
