package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;

public interface RecordShopService {
    public Album createAlbum(Album album);
    public Album updateAlbum(Album album);
    public void deleteAlbum(Album album);
    public Album getAlbumById(Long id);
    public Album getAlbumByGenre(String genre);
    public Album getAlbumByArtistName(Long artistId);
    public Album getAlbumByReleasedDate(Long artistId);
    public Album getAlbumByArtistId(Long artistId);

}
