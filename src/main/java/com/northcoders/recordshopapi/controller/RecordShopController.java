package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.AlbumRequestPojo;
import com.northcoders.recordshopapi.model.AlbumRequestUpdatePojo;
import com.northcoders.recordshopapi.model.AlbumResponsePojo;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
    @GetMapping("/albums/artist/{artistName}")
    public ResponseEntity<List<Album>> getAlbumsByArtistName(@PathVariable String artistName) {
        return new ResponseEntity<>(recordShopService.getAlbumByArtistName(artistName),HttpStatus.OK);
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

}
