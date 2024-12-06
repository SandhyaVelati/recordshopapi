package com.northcoders.recordshopapi.repository;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//due to relationships, not really necessary service
@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Optional<Artist> findByArtistName(String name);

}
