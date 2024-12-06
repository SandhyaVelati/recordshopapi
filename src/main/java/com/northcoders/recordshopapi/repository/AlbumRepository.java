package com.northcoders.recordshopapi.repository;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

    Optional<Album> findByAlbumName(String title);
    List<Album> findByGenre(Genre genre);
    @Query(value = "SELECT * FROM album where artist_id = ?1 order by id desc;", nativeQuery = true)
    List<Album> findByArtistId(Long artistId);

    @Query(value = "SELECT * FROM album a where EXTRACT(YEAR FROM a.release_date) = ?1", nativeQuery = true)
    List<Album> findByReleaseDate(Integer releaseDate);
}
