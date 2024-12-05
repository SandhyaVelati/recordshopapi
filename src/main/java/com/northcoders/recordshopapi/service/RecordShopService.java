package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RecordShopService {
    public Album createAlbum(Album album);
    public Album updateAlbum(Album album);
    public void deleteAlbum(Album album);
    public List<Album> getAlbums();
    public Optional<Album> getAlbumById(Long id);
    public Album getAlbumByGenre(String genre);

    List<Album> getAlbumByArtistName(String name);

    public Album getAlbumByReleasedDate(Long artistId);
    public Album getAlbumByArtistId(Long artistId);

}
