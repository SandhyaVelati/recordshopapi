package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.Exception.AlbumNotFoundException;
import com.northcoders.recordshopapi.model.*;
import com.northcoders.recordshopapi.repository.AlbumRepository;
import com.northcoders.recordshopapi.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecordShopServiceImpl implements RecordShopService{

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;
    @Override
    public AlbumResponsePojo createAlbum(AlbumRequestPojo album) {
        Album albumData = mapDTOToData(album);
        Album createdAlbum = albumRepository.save(albumData);
        return mapDataToDTO(createdAlbum);
    }
    public AlbumResponsePojo mapDataToDTO(Album album){
        return  new AlbumResponsePojo.Builder().setAlbumName(album.getAlbumName())
                .setReleaseDate(album.getReleaseDate())
                .setId(album.getId())
                .setGenre(album.getGenre())
                .setArtist(new ArtistPojo.Builder().setArtistName(album.getArtist().getArtistName()).setId(album.getArtist().getId()).build())
                .build();
//         new AlbumResponsePojo();
    }
    public Album mapDTOToData(AlbumRequestPojo albumRequestPojo){
        Artist artist = artistRepository.findByArtistName(albumRequestPojo.getArtist().getArtistName())
                .orElseGet(() ->{
                   Artist artistNew = new Artist();
                   artistNew.setArtistName(albumRequestPojo.getArtist().getArtistName());
                   return artistNew;
                });
        return Album.builder().albumName(albumRequestPojo.getAlbumName())
                 .releaseDate(albumRequestPojo.getReleaseDate())
                .id(albumRequestPojo.getId())
                .genre(albumRequestPojo.getGenre())
                .createdDt(LocalDateTime.now())
                .modifiedDt(LocalDateTime.now())
                .artist(artist)
                .build();
    }

    @Override
    public AlbumResponsePojo updateAlbum(Long id,AlbumRequestUpdatePojo albumRequestPojo) {
        Optional<Album> albumById = albumRepository.findById(id);
        if(albumById.isEmpty()){
            throw new AlbumNotFoundException("Album with id: "+ id + " not found");
        }
        Album existingAlbum = albumById.get();
        existingAlbum.setGenre(albumRequestPojo.getGenre());
        if(albumRequestPojo.getReleaseDate() != null){
            existingAlbum.setReleaseDate(albumRequestPojo.getReleaseDate());
        }
        if (albumRequestPojo.getArtist() !=null && albumRequestPojo.getArtist().getArtistName() != null ){
            Optional<Artist> existingArtist = artistRepository.findByArtistName(albumRequestPojo.getArtist().getArtistName());
            if (existingArtist.isPresent()) {
                existingAlbum.setArtist(existingArtist.get());
            } else {
                Artist newArtist = new Artist();
                newArtist.setArtistName(albumRequestPojo.getArtist().getArtistName());
                Artist savedArtist = artistRepository.save(newArtist);
                existingAlbum.setArtist(savedArtist);
            }
        }
        Album savedAlbum = albumRepository.save(existingAlbum);
        return mapDataToDTO(savedAlbum);

    }
    @Override
    public void deleteAlbum(Long id) {
        Optional<Album> byId = albumRepository.findById(id);
        if(byId.isEmpty()){
            throw new AlbumNotFoundException("Album with id: "+ id + " not found");
        }
        albumRepository.deleteById(id);

    }

    @Override
    public List<AlbumResponsePojo> getAlbums() {
        List<Album> allRecords = (List<Album>)albumRepository.findAll();
        return allRecords.stream().map(album -> {
            return new AlbumResponsePojo.Builder().setReleaseDate(album.getReleaseDate())
                    .setGenre(album.getGenre())
                    .setArtist(
                            new ArtistPojo.Builder().setArtistName(album.getArtist().getArtistName())
                                    .setId(album.getArtist().getId())
                                    .build())
                    .setAlbumName(album.getAlbumName())
                    .setId(album.getId()).build();
        }).toList();
    }

    @Override
    public AlbumResponsePojo getAlbumById(Long id) {
        Optional<Album> albumById = albumRepository.findById(id);
        return albumById.map(
                existingAlbum -> {
                    return new AlbumResponsePojo.Builder().setReleaseDate(existingAlbum.getReleaseDate())
                            .setGenre(existingAlbum.getGenre())
                            .setArtist(
                                    new ArtistPojo.Builder().setArtistName(existingAlbum.getArtist().getArtistName())
                                    .setId(existingAlbum.getArtist().getId())
                                    .build())
                            .setAlbumName(existingAlbum.getAlbumName())
                            .setId(existingAlbum.getId()).build();
                })
                .orElseThrow(()-> new AlbumNotFoundException("Album with id: "+ id + " not found"));
    }
    @Override
    public List<AlbumResponsePojo>  getAlbumByGenre(String genre) {
        List<Album> albumsByGenre = albumRepository.findByGenre(Genre.valueOf(genre));
        return albumsByGenre.stream().map(this::mapDataToDTO).toList();
    }

    @Override
    public List<AlbumResponsePojo> getAlbumByArtistName(String name) {
        Optional<Artist> byArtistName = artistRepository.findByArtistName(name);
        if(byArtistName.isEmpty()){
            throw new AlbumNotFoundException("Artist not found, try searching with existing artists");
        }
        Long artistId = byArtistName.get().getId();
        return getAlbumByArtistId(artistId);

    }
    @Override
    public Album getAlbumByReleasedDate(Long artistId) {
        return null;
    }
    @Override
    public List<AlbumResponsePojo> getAlbumByArtistId(Long artistId) {
        List<Album> albumsByArtistId = albumRepository.findByArtistId(artistId);
        if(albumsByArtistId.isEmpty()){
            throw new AlbumNotFoundException("Albums requested for the artist id: "+ artistId+ " not found");
        }
        return albumsByArtistId.stream().map(this::mapDataToDTO).toList();
    }
}
