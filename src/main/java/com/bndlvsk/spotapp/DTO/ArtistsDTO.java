package com.bndlvsk.spotapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.bndlvsk.spotapp.model.spotify.Artist;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ArtistsDTO {

    private String total;

    @JsonProperty("items")
    private List<Artist> artists;

    private String range;

    private LocalDate date;

    private LocalDate lastVisit;

    public ArtistsDTO withRange(String range) {
        this.range = range;
        return this;
    }

    public ArtistsDTO withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public ArtistsDTO withLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
        return this;
    }
}