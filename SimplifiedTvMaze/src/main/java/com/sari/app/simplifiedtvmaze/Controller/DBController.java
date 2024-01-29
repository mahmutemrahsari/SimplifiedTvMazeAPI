package com.sari.app.simplifiedtvmaze.Controller;

import com.sari.app.simplifiedtvmaze.Exception.NotFoundException;
import com.sari.app.simplifiedtvmaze.Models.Show;
import com.sari.app.simplifiedtvmaze.Repository.ShowRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/db")
public class DBController {
    private final ShowRepository showRepository;

    public DBController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @GetMapping(value = "/shows")
    public ResponseEntity<List<Show>> getShows() {
        List<Show> shows = showRepository.findAll();

        if (shows.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(shows);
        }
    }

    @GetMapping(value = "/show/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        Show show = showRepository.findById(id)
                .orElse(null);

        if (show != null) {
            return ResponseEntity.ok(show);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(value = "/save")
    public ResponseEntity<String> saveShow(@RequestBody @Valid Show show) {
        try {
            showRepository.save(show);
            return ResponseEntity.ok("Show saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save show");
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updateShow(@PathVariable Long id, @Valid @RequestBody Show updatedShow) {
        if (!showRepository.existsById(id)) {
            throw new NotFoundException("Show with id " + id + " not found.");
        }
        try {
            Show existingShow = showRepository.findById(id).orElseThrow(() -> new NotFoundException("Show with id " + id + " not found."));

            existingShow.setName(updatedShow.getName());
            existingShow.setGenres(updatedShow.getGenres());

            // Lagre den oppdaterte Show i databasen
            showRepository.save(existingShow);

            return ResponseEntity.ok("Show updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update show");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id) {
        try {
            if (showRepository.existsById(id)) {
                showRepository.deleteById(id);
                return ResponseEntity.ok("Show with id " + id + " deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete show with id " + id);
        }
    }
}
