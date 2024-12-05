package com.northcoders.recordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.model.Artist;
import com.northcoders.recordshopapi.model.Genre;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import jakarta.persistence.PostRemove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class RecordShopControllerTest {
    @Mock
    private RecordShopServiceImpl mockRecordShopService;
    @InjectMocks
    private RecordShopController recordShopController;
    @Autowired
    private MockMvc mockMvcController;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(recordShopController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllAlbums() throws Exception {
        List<Album> albums = new ArrayList<>();
        Artist artist1 = new Artist();
        artist1.setId(1L);
        artist1.setArtistName("eghfg");
        albums.add(new Album(1L,"abc", LocalDate.now(),
                Genre.HIP_HOP,artist1,
                LocalDateTime.now(), LocalDateTime.now()));
        when(mockRecordShopService.getAlbums()).thenReturn(albums);
        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("abc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("HIP_HOP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist.artistName").value("eghfg"));
    }

}