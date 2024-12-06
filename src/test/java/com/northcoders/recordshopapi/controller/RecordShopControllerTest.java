package com.northcoders.recordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.northcoders.recordshopapi.Exception.AlbumNotFoundException;
import com.northcoders.recordshopapi.Exception.GlobalExceptionHandler;
import com.northcoders.recordshopapi.model.*;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class RecordShopControllerTest {
    @Mock
    private RecordShopServiceImpl mockRecordShopService;
    @InjectMocks
    private RecordShopController recordShopController;
//    @Autowired
    private MockMvc mockMvcController;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(recordShopController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }
    @Test
    public void testGetAllAlbums() throws Exception {
        List<AlbumResponsePojo> albums = new ArrayList<>();
        Artist artist1 = new Artist();
        artist1.setId(1L);
        artist1.setArtistName("eghfg");
        albums.add(new AlbumResponsePojo.Builder().setId(1L)
                .setAlbumName("abc")
                .setReleaseDate(LocalDate.now())
                .setGenre(Genre.HIP_HOP)
                .setArtist(new ArtistPojo.Builder().setArtistName("eghfg").setId(1L).build()).build());
        albums.add(new AlbumResponsePojo.Builder().setId(2L)
                .setAlbumName("Ritchie")
                .setReleaseDate(LocalDate.now())
                .setGenre(Genre.BLUES)
                .setArtist(new ArtistPojo.Builder().setArtistName("eghfg").setId(1L).build()).build());
        when(mockRecordShopService.getAlbums()).thenReturn(albums);
        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("abc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("abc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("HIP_HOP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value("HIP_HOP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist.artistName").value("eghfg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artist.artistName").value("eghfg"));

    }
    @Test
    public void testGetAllAlbums_noMatchingData() throws Exception {
        // Arrange
        when(mockRecordShopService.getAlbums()).thenReturn(new ArrayList<>());

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void testGetAlbumById() throws Exception {
        // Mock AlbumResponsePojo
        ArtistPojo artist = new ArtistPojo.Builder()
                .setId(1L)
                .setArtistName("Yash")
                .build();
        AlbumResponsePojo album = new AlbumResponsePojo.Builder()
                .setId(1L)
                .setAlbumName("TumHare the")
                .setGenre(Genre.CLASSICAL)
                .setReleaseDate(LocalDate.now())
                .setArtist(artist)
                .build();

        when(mockRecordShopService.getAlbumById(1L)).thenReturn(album);
        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("TumHare the"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("CLASSICAL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist.artistName").value("Yash"));

        verify(mockRecordShopService, times(1)).getAlbumById(1L);
    }

    @Test
    public void testGetAlbumById_noMatchingRecord() throws Exception {
        when(mockRecordShopService.getAlbumById(1L)).thenThrow(new AlbumNotFoundException("Album with id: 1 not found"));
        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Album with id: 1 not found"));
        verify(mockRecordShopService, times(1)).getAlbumById(1L);
    }

    @Test
    public void testCreateAlbum_success() throws Exception {
        ArtistPojo artist = new ArtistPojo.Builder().setId(1L).setArtistName("John Doe").build();
        AlbumRequestPojo albumRequest = new AlbumRequestPojo(1L,"Great Album", Genre.ROCK, LocalDate.now(), artist);
        AlbumResponsePojo albumResponse = new AlbumResponsePojo.Builder()
                .setId(1L)
                .setAlbumName("Great Album")
                .setGenre(Genre.ROCK)
                .setReleaseDate(LocalDate.now())
                .setArtist(artist)
                .build();

        when(mockRecordShopService.createAlbum(albumRequest)).thenReturn(albumResponse);
        mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/album")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(albumRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Great Album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("ROCK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist.artistName").value("John Doe"));

        verify(mockRecordShopService, times(1)).createAlbum(albumRequest);
    }

    @Test
    public void testUpdateAlbum_success() throws Exception{
        //arrange

        AlbumRequestUpdatePojo albumRequestPojo = new AlbumRequestUpdatePojo();
        albumRequestPojo.setAlbumName("TumHare the");
        albumRequestPojo.setGenre(Genre.ROCK);
        albumRequestPojo.setReleaseDate(LocalDate.now());
        ArtistPojo artist = new ArtistPojo.Builder()
                .setId(1L)
                .setArtistName("Yash")
                .build();
        AlbumResponsePojo albumResponsePojo = new AlbumResponsePojo.Builder()
                .setId(1L)
                .setAlbumName("TumHare the")
                .setGenre(Genre.ROCK)
                .setReleaseDate(LocalDate.now())
                .setArtist(artist)
                .build();
        when(mockRecordShopService.updateAlbum(eq(1L),any(AlbumRequestUpdatePojo.class))).thenReturn(albumResponsePojo);
        //act
        mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/album/1").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(albumRequestPojo)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("TumHare the"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("ROCK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist.artistName").value("Yash"));
        verify(mockRecordShopService, times(1)).updateAlbum(eq(1L), any(AlbumRequestUpdatePojo.class));
    }
}