package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.AlbumRequestPojo;
import com.northcoders.recordshopapi.model.AlbumRequestUpdatePojo;
import com.northcoders.recordshopapi.model.AlbumResponsePojo;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RecordShopService {
    public AlbumResponsePojo createAlbum(AlbumRequestPojo album);
    public AlbumResponsePojo updateAlbum(Long id, AlbumRequestUpdatePojo album);
    public void deleteAlbum(Long id);
    public List<AlbumResponsePojo> getAlbums();
    public AlbumResponsePojo getAlbumById(Long id);
    public List<AlbumResponsePojo>  getAlbumByGenre(String genre);

    List<AlbumResponsePojo> getAlbumByArtistName(String name);

    public Album getAlbumByReleasedDate(Long artistId);
    public List<AlbumResponsePojo> getAlbumByArtistId(Long artistId);

}
