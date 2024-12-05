package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RecordShopController {
    @Autowired
    private RecordShopServiceImpl recordShopService;
    @PostMapping("/album")
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
        Album createdAlbum = recordShopService.createAlbum(album);
        return new ResponseEntity<>(createdAlbum, HttpStatus.CREATED);
    }
    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAllAlbums() {
        return new ResponseEntity<>(recordShopService.getAlbums(),HttpStatus.OK);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
         return recordShopService.getAlbumById(id)
                 .map(album -> new ResponseEntity<>(album, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/albums/artist/{artistName}")
    public ResponseEntity<List<Album>> getAlbumsByArtistName(@PathVariable String artistName) {
        return new ResponseEntity<>(recordShopService.getAlbumByArtistName(artistName),HttpStatus.OK);
    }

    @PutMapping("/album/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album album){
         return new ResponseEntity<>(recordShopService.updateAlbum(album),HttpStatus.OK);
    }

    @DeleteMapping("/album")
    public ResponseEntity<String> deleteAlbum(@RequestBody Album album){
        recordShopService.deleteAlbum(album);
        return new ResponseEntity<String>("deleted album successfully", HttpStatus.OK);
    }

}
