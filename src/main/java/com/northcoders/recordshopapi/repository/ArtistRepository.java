package com.northcoders.recordshopapi.repository;

import com.northcoders.recordshopapi.model.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


//due to relationships, not really necessary service
@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
}
