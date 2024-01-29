package com.sari.app.simplifiedtvmaze;

import com.sari.app.simplifiedtvmaze.Controller.TVMazeController;
import com.sari.app.simplifiedtvmaze.Models.Show;
import com.sari.app.simplifiedtvmaze.Service.TVMazeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(TVMazeController.class)
class TVMazeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TVMazeService tvMazeService;

    private Show sampleShow;

    @BeforeEach
    void setUp() {
        // Initialize a sample Show for testing
        sampleShow = new Show();
        sampleShow.setId(1L);
        sampleShow.setName("Sample Show");
        sampleShow.setGenres(Arrays.asList("Genre1", "Genre2"));
        sampleShow.setWeight(100);
        sampleShow.setStatus("Active");
    }

    @Test
    void testGetShowById() {
        // Mock the service response
        when(tvMazeService.getShowById(1L)).thenReturn(Mono.just(sampleShow));

        // Perform the GET request and verify the response
        webTestClient.get()
                .uri("/api/shows/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Show.class)
                .isEqualTo(sampleShow);
    }

    @Test
    void testGetPopularShows() {
        // Mock the service response for getAllShows
        when(tvMazeService.getAllShows()).thenReturn(Mono.just(new Show[]{sampleShow}));

        // Perform the GET request and verify the response
        webTestClient.get()
                .uri("/api/shows/popular/{quantity}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Show.class)
                .contains(sampleShow);
    }
}
