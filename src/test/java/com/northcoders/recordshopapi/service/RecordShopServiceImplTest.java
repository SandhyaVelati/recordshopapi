package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.*;
import com.northcoders.recordshopapi.repository.AlbumRepository;
import com.northcoders.recordshopapi.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecordShopServiceImplTest {
    @Mock
    private AlbumRepository mockAlbumRepository;

    @Mock
    private ArtistRepository mockArtistRepository;

    @InjectMocks
    private RecordShopServiceImpl recordShopService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAlbums(){
        //Arrange
        List<Album> albums = new ArrayList<>();
        Artist artist1 = new Artist();
        artist1.setId(1L);
        artist1.setArtistName("Dua Lipa");
        Artist artist2 = new Artist();
        artist2.setId(2L);
        artist2.setArtistName("Ed Sheeran");
        Album album1 = new Album();
        album1.setId(1L);
        album1.setAlbumName("Levitating");
        album1.setGenre(Genre.POP);
        album1.setReleaseDate(LocalDate.of(2020, 3, 27));
        album1.setCreatedDt(null);
        album1.setModifiedDt(null);
        album1.setArtist(artist1);
        Album album2 = new Album();
        album2.setId(2L);
        album2.setAlbumName("Perfect");
        album2.setGenre(Genre.POP);
        album2.setReleaseDate(LocalDate.of(2017, 3, 3));
        album2.setModifiedDt(null);
        album2.setCreatedDt(null);
        album2.setArtist(artist2);
        albums.add(album1);
        albums.add(album2);
        when(mockAlbumRepository.findAll()).thenReturn(albums);
        List<AlbumResponsePojo> actualAlbumsResponseList = recordShopService.getAlbums();

        assertThat(actualAlbumsResponseList).hasSize(2);
        verify(mockAlbumRepository, times(1)).findAll();
        //verifying random properties as DTO and Album are diff types
        AlbumResponsePojo albumResponse1 = actualAlbumsResponseList.get(0);
        assertThat(albumResponse1.getAlbumName()).isEqualTo("Levitating");
        assertThat(albumResponse1.getGenre()).isEqualTo(Genre.POP);
        AlbumResponsePojo albumResponse2 = actualAlbumsResponseList.get(1);
        assertThat(albumResponse2.getId()).isEqualTo(2L);
        assertThat(albumResponse2.getAlbumName()).isEqualTo("Perfect");
    }

    @Test
    public void testGetAlbumById(){
        //arrange
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setArtistName("Dua Lipa");
        Album album = new Album();
        album.setId(1L);
        album.setAlbumName("Levitating");
        album.setGenre(Genre.POP);
        album.setReleaseDate(LocalDate.of(2021, 2, 17));
        album.setArtist(artist);
        //act
        when(mockAlbumRepository.findById(1L)).thenReturn(Optional.of(album));
        AlbumResponsePojo albumResponse = recordShopService.getAlbumById(1L);
        //assert
        verify(mockAlbumRepository, times(1)).findById(1L);
        assertThat(albumResponse).isNotNull();
        assertThat(albumResponse.getId()).isEqualTo(1L);
        assertThat(albumResponse.getAlbumName()).isEqualTo("Levitating");
        assertThat(albumResponse.getGenre()).isEqualTo(Genre.POP);
        assertThat(albumResponse.getReleaseDate()).isEqualTo(LocalDate.of(2021, 2, 17));
        assertThat(albumResponse.getArtist().getArtistName()).isEqualTo("Dua Lipa");
        assertThat(albumResponse.getArtist().getId()).isEqualTo(1L);
    }
    @Test
    public void testCreateAlbum() {
        ArtistPojo artist = new ArtistPojo.Builder().setId(1L).setArtistName("Dua Lipa").build();
        Artist artistNew = new Artist();
        AlbumRequestPojo albumRequest = new AlbumRequestPojo(1L,"Great Album", Genre.ROCK, LocalDate.now(), artist);
        artistNew.setArtistName(albumRequest.getArtist().getArtistName());
        artistNew.setId(albumRequest.getArtist().getId());
        AlbumResponsePojo albumResponseExpected = new AlbumResponsePojo.Builder()
                .setId(1L)
                .setAlbumName("Great Album")
                .setGenre(Genre.ROCK)
                .setReleaseDate(LocalDate.now())
                .setArtist(artist)
                .build();
        Album album = Album.builder().albumName(albumRequest.getAlbumName())
                .releaseDate(albumRequest.getReleaseDate())
                .id(albumRequest.getId())
                .genre(albumRequest.getGenre())
                .createdDt(LocalDateTime.now())
                .modifiedDt(LocalDateTime.now())
                .artist(artistNew)
                .build();
        when(mockArtistRepository.findByArtistName(artist.getArtistName())).thenReturn(Optional.of(artistNew));
        when(mockAlbumRepository.save(any(Album.class))).thenReturn(album);
        AlbumResponsePojo albumResponsePojo = recordShopService.createAlbum(albumRequest);
        assertThat(albumResponsePojo).isEqualTo(albumResponseExpected);
        verify(mockAlbumRepository, times(1)).save(any(Album.class));
    }
    @Test
    void testCreateNullAlbum(){
        assertThrows(NullPointerException.class, () -> recordShopService.createAlbum(null));
    }

    @Test
    public void testCreateAlbum_duplicateAlbum() {
        ArtistPojo artist = new ArtistPojo.Builder().setId(1L).setArtistName("Dua Lipa").build();
        Artist artistEntity = new Artist();
        AlbumRequestPojo albumRequest = new AlbumRequestPojo(1L,"Great Album", Genre.ROCK, LocalDate.now(), artist);
        artistEntity.setArtistName("Dua Lipa");
        artistEntity.setId(1L);
        AlbumResponsePojo albumResponseExpected = new AlbumResponsePojo.Builder()
                .setId(1L)
                .setAlbumName("Great Album")
                .setGenre(Genre.ROCK)
                .setReleaseDate(LocalDate.now())
                .setArtist(artist)
                .build();
        Album album = Album.builder().albumName("Great Album")
                .releaseDate(albumRequest.getReleaseDate())
                .id(1L)
                .genre(Genre.BLUES)
                .createdDt(LocalDateTime.now())
                .modifiedDt(LocalDateTime.now())
                .artist(artistEntity)
                .build();
        when(mockArtistRepository.findByArtistName(artist.getArtistName())).thenReturn(Optional.of(artistEntity));
        when(mockAlbumRepository.save(any(Album.class))).thenThrow(DataIntegrityViolationException.class);
        //AlbumResponsePojo albumResponsePojo = recordShopService.createAlbum(albumRequest);
       assertThatThrownBy(()->recordShopService.createAlbum(albumRequest)).isInstanceOf(DataIntegrityViolationException.class);
        verify(mockAlbumRepository, times(1)).save(any(Album.class));
    }

    @Test
    public void testUpdateAlbum_success(){
        AlbumRequestUpdatePojo albumRequest = new AlbumRequestUpdatePojo();
        albumRequest.setAlbumName("New Album");
        albumRequest.setGenre(Genre.ROCK);
        albumRequest.setReleaseDate(LocalDate.now());
        //artist in requets
        ArtistPojo artistPojo = new ArtistPojo();
        artistPojo.setArtistName("Old Artist");
        albumRequest.setArtist(artistPojo);

        Album existingAlbum = new Album();
        existingAlbum.setId(1L);
        existingAlbum.setAlbumName("Old Album");
        //artist in db
        Artist existingArtist = new Artist();
        existingArtist.setId(1L);
        existingArtist.setArtistName("Old Artist");

        when(mockAlbumRepository.findById(1L)).thenReturn(Optional.of(existingAlbum));
        when(mockArtistRepository.findByArtistName("Old Artist")).thenReturn(Optional.of(existingArtist));
        when(mockAlbumRepository.save(existingAlbum)).thenReturn(existingAlbum);

        AlbumResponsePojo updatedAlbum = recordShopService.updateAlbum(1L, albumRequest);

        assertNotNull(updatedAlbum);
        assertEquals("Old Album", updatedAlbum.getAlbumName()); //album name cannot be updated. update is done using id
        assertEquals(Genre.ROCK, updatedAlbum.getGenre());
        assertEquals("Old Artist", updatedAlbum.getArtist().getArtistName());
        assertEquals(1L, updatedAlbum.getArtist().getId());

        verify(mockAlbumRepository, times(1)).findById(1L);
        verify(mockArtistRepository, times(1)).findByArtistName("Old Artist");
        verify(mockAlbumRepository, times(1)).save(existingAlbum);


    }

    @Test
    public void testUpdateAlbum_nonExisting_artistUpdate(){
        AlbumRequestUpdatePojo albumRequest = new AlbumRequestUpdatePojo();
        albumRequest.setAlbumName("Old Album");
        albumRequest.setGenre(Genre.ROCK);
        albumRequest.setReleaseDate(LocalDate.now());
        //artist in requets
        ArtistPojo artistPojo = new ArtistPojo();
        artistPojo.setArtistName("New Artist");
        albumRequest.setArtist(artistPojo);

        Album existingAlbum = new Album();
        existingAlbum.setId(1L);
        existingAlbum.setAlbumName("Old Album");
        //artist in db
        Artist existingArtist = new Artist();
        existingArtist.setId(1L);
        existingArtist.setArtistName("Old Artist");

        when(mockAlbumRepository.findById(1L)).thenReturn(Optional.of(existingAlbum));
        when(mockArtistRepository.findByArtistName("New Artist")).thenReturn(Optional.empty());
        when(mockAlbumRepository.save(existingAlbum)).thenReturn(existingAlbum);

        //new Artist Entity created in mock
        Artist newArtist = new Artist();
        newArtist.setArtistName("New Artist");
        newArtist.setId(2L);
        when(mockArtistRepository.save(any(Artist.class))).thenReturn(newArtist);

        AlbumResponsePojo updatedAlbum = recordShopService.updateAlbum(1L, albumRequest);

        assertNotNull(updatedAlbum);
        assertEquals("Old Album", updatedAlbum.getAlbumName()); //album name cannot be updated. update is done using id
        assertEquals("New Artist", updatedAlbum.getArtist().getArtistName());
        assertNotNull(updatedAlbum.getArtist().getId());

        verify(mockAlbumRepository, times(1)).findById(1L);
        verify(mockArtistRepository, times(1)).findByArtistName("New Artist");
        verify(mockArtistRepository, times(1)).save(any(Artist.class));
        verify(mockAlbumRepository, times(1)).save(any(Album.class));
    }

}
