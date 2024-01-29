package com.sari.app.simplifiedtvmaze.Controller;

import com.sari.app.simplifiedtvmaze.Models.Show;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.List;

// Interface for TVMazeController klassen
@RestController
@RequestMapping("/api/shows")
public interface ITVMazeController {
    @GetMapping("/{id}")
    Mono<Show> getShowById(@PathVariable @Valid @NotNull Long id);
    @GetMapping("/all")
    Mono<Show[]> getAllShows();
    @GetMapping("/popular/{quantity}")
    List<Show> getPopularShows(@PathVariable @Valid @NotNull int quantity);
    @GetMapping("/genre/{genre}")
    List<Show> getShowsByGenre(@PathVariable @Valid @NotEmpty String genre);
}
