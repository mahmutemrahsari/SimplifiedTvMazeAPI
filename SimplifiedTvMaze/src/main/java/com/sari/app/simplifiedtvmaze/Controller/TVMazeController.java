package com.sari.app.simplifiedtvmaze.Controller;

import com.sari.app.simplifiedtvmaze.Exception.NotFoundException;
import com.sari.app.simplifiedtvmaze.Models.Show;
import com.sari.app.simplifiedtvmaze.Service.TVMazeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

// API-endepunkter for å hente data fra https://api.tvmaze.com
@RestController
@RequestMapping("/api/shows")
public class TVMazeController implements ITVMazeController {
    private final TVMazeService tvMazeService;

    @Autowired
    public TVMazeController(TVMazeService tvMazeService) {
        this.tvMazeService = tvMazeService;
    }

    @Override
    @GetMapping("/{id}")
    public Mono<Show> getShowById(@PathVariable @Valid @NotNull Long id) {
        return tvMazeService.getShowById(id);
    }
    // henter ut alle showene 
    @GetMapping("/all")
    public Mono<Show[]> getAllShows() {
        return tvMazeService.getAllShows();
    }
    // henter ut gitte antall showene som er sortert etter popularitet
    @GetMapping("/popular/{quantity}")
    public List<Show> getPopularShows(@PathVariable @Valid @NotNull int quantity) {
        List<Show> popShows = new ArrayList<>();
        Show[] allShows = Objects.requireNonNull(getAllShows().block());
        for (Show aShow : allShows) {
            Show singleShow = new Show();
            singleShow.setId(aShow.getId());
            singleShow.setName(aShow.getName());
            singleShow.setGenres(aShow.getGenres());
            singleShow.setWeight(aShow.getWeight());
            singleShow.setStatus(aShow.getStatus());
            popShows.add(singleShow);
        }
        //sortert etter "weight" for å hente de meste populare showene
        popShows.sort(Comparator.comparing(Show::getWeight).reversed());
        if (quantity <= popShows.size()) {
            List<Show> portionOfShowArray = new ArrayList<>();
            for (int i = 0; i < quantity; i++) {
                portionOfShowArray.add(popShows.get(i));
            }
            return portionOfShowArray;
        } else {
            throw new  NotFoundException("Quantity of shows can't be more than " + popShows.size() + " Please lower the quantity!");
        }
    }
    // henter ut showene gjennom gitte sjanger
    @GetMapping("/genre/{genre}")
    public List<Show> getShowsByGenre(@PathVariable @Valid @NotEmpty String genre) {
        String[] validGenres = {
                "Action", "Adult", "Adventure", "Anime", "Children", "Comedy", "Crime", "DIY", "Drama",
                "Espionage", "Family", "Fantasy", "Food", "History", "Horror", "Legal", "Medical", "Music", "Mystery",
                "Nature", "Romance", "Science-Fiction", "Sports", "Supernatural", "Thriller", "Travel", "Western", "War"
        };

        for (String s : validGenres) {
            if (s.toLowerCase().equals(genre) || s.equals(genre) || s.toUpperCase().equals(genre)) {
                List<Show> allShows = Arrays.stream(Objects.requireNonNull(getAllShows().block())).toList();
                return allShows.stream().filter(show -> show.getGenres().contains(genre.substring(0, 1).toUpperCase()
                        + genre.substring(1).toLowerCase())).collect(Collectors.toList());
            }
        }
        throw new  NotFoundException("Genre "+  genre +" not found!");
    }
}
