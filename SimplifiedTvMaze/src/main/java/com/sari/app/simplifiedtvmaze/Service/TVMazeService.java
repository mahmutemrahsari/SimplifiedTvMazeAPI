package com.sari.app.simplifiedtvmaze.Service;

import com.sari.app.simplifiedtvmaze.Exception.NotFoundException;
import com.sari.app.simplifiedtvmaze.Models.Show;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//en tjenesteklasse som bruker WebClient for å kommunisere med TVMaze API.

@Service
public class TVMazeService implements ITVMazeService {
    private final WebClient webClient;

    public TVMazeService(WebClient.Builder webClientBuilder, @Value("${tvmaze.api.base-url}") String tvmazeApiBaseUrl) {
        this.webClient = webClientBuilder.baseUrl(tvmazeApiBaseUrl).build();
    }

    @PreAuthorize("hasRole('USER')")
    public Mono<Show> getShowById(Long showId) {
        return webClient.get().uri("/shows/{id}", showId).retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new NotFoundException("Show not found")))
                .bodyToMono(Show.class);
    }
    @PreAuthorize("hasRole('USER')")
    public Mono<Show[]> getAllShows() {
        String url = "/shows";
        return webClient.get().uri(uriBuilder -> uriBuilder.path(url)
                        .build()).retrieve().onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new NotFoundException("Shows not found")))
                .bodyToMono(Show[].class);
    }
}

