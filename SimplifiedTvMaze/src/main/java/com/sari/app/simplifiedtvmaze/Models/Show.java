package com.sari.app.simplifiedtvmaze.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity // Angir at klassen er en JPA-entitetsklasse.
@Table(name = "shows") // Angir navnet på databasetabellen som denne entitetsklassen skal mappe til.
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Definerer hvordan primærnøkkelen genereres.
    private Long id;

    @NotEmpty
    private String name;

    @ElementCollection // Indikerer at genres-feltet er en samling av enkle verdier, og det skal være en separat tabell for denne samlingen.
    @CollectionTable(name = "show_genres", joinColumns = @JoinColumn(name = "show_id"))
    @Column(name = "genre")
    private List<@NotEmpty String> genres;

    private String status;
    private int weight; // TVMaze APIen bruker for å ranke populariteten av et show

    private String message;
    private int code;

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
