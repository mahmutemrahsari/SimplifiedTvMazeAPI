package com.sari.app.simplifiedtvmaze;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sari.app.simplifiedtvmaze.Controller.DBController;
import com.sari.app.simplifiedtvmaze.Models.Show;
import com.sari.app.simplifiedtvmaze.Repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(DBController.class)
class DBControllerTest {

    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private DBController dbController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getShows_ReturnsListOfShows_WhenShowsExist() throws Exception {
        List<Show> shows = new ArrayList<>();
        shows.add(new Show());
        shows.add(new Show());

        when(showRepository.findAll()).thenReturn(shows);

        mockMvc.perform(get("/db/shows"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(showRepository, times(1)).findAll();
    }

    @Test
    void getShowById_ReturnsShow_WhenShowExists() throws Exception {
        Show mockShow = new Show();
        mockShow.setId(1L);
        mockShow.setName("Test Show");

        when(showRepository.findById(1L)).thenReturn(Optional.of(mockShow));

        mockMvc.perform(get("/db/show/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Show"));

        verify(showRepository, times(1)).findById(1L);
    }

    @Test
    void saveShow_ReturnsOk_WhenSaveIsSuccessful() throws Exception {
        Show mockShow = new Show();
        mockShow.setName("Test Show");

        when(showRepository.save(any(Show.class))).thenReturn(mockShow);

        mockMvc.perform(post("/db/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockShow)))
                .andExpect(status().isOk())
                .andExpect(content().string("Show saved successfully"));

        verify(showRepository, times(1)).save(any(Show.class));
    }

    @Test
    void updateShow_ReturnsOk_WhenUpdateIsSuccessful() throws Exception {
        Show mockExistingShow = new Show();
        mockExistingShow.setId(1L);
        mockExistingShow.setName("Existing Show");

        Show mockUpdatedShow = new Show();
        mockUpdatedShow.setName("Updated Show");

        when(showRepository.existsById(1L)).thenReturn(true);
        when(showRepository.findById(1L)).thenReturn(Optional.of(mockExistingShow));
        when(showRepository.save(any(Show.class))).thenReturn(mockUpdatedShow);

        mockMvc.perform(put("/db/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUpdatedShow)))
                .andExpect(status().isOk())
                .andExpect(content().string("Show updated successfully"));

        verify(showRepository, times(1)).existsById(1L);
        verify(showRepository, times(1)).findById(1L);
        verify(showRepository, times(1)).save(any(Show.class));
    }

    @Test
    void deleteShow_ReturnsOk_WhenDeleteIsSuccessful() throws Exception {
        when(showRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/db/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Show with id 1 deleted successfully."));

        verify(showRepository, times(1)).existsById(1L);
        verify(showRepository, times(1)).deleteById(1L);
    }

    // Additional tests can be added as needed
}
