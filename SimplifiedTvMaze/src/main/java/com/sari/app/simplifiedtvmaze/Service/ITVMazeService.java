package com.sari.app.simplifiedtvmaze.Service;

import com.sari.app.simplifiedtvmaze.Models.Show;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Mono;

public interface ITVMazeService {
    @PreAuthorize("hasRole('USER')")
    Mono<Show> getShowById(Long showId);
    @PreAuthorize("hasRole('USER')")
    public Mono<Show[]> getAllShows();
}
