package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordShopServiceImpl implements RecordShopService{

    @Autowired
    private AlbumRepository albumRepository;
    @Override
    public Album createAlbum(Album album) {
        return null;
    }
    @Override
    public Album updateAlbum(Album album) {
        return null;
    }
    @Override
    public void deleteAlbum(Album album) {
        albumRepository.delete(album);
    }

    @Override
    public List<Album> getAlbums() {
        return List.of(new Album());
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        return null;
    }
    @Override
    public Album getAlbumByGenre(String genre) {
        return null;
    }

    @Override
    public List<Album> getAlbumByArtistName(String name) {
        return null;
    }
    @Override
    public Album getAlbumByReleasedDate(Long artistId) {
        return null;
    }
    @Override
    public Album getAlbumByArtistId(Long artistId) {
        return null;
    }
}
