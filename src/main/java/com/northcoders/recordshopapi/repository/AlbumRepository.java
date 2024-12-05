package com.northcoders.recordshopapi.repository;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {
    List<Album> findByArtistName(String artistName);
    Optional<Album> findByAlbumName(String title);
    List<Album> findByGenre(Genre genre);
    List<Album> findByArtistId(Long artistId);
    List<Album> findByReleaseDate(LocalDate releaseDate);
}
