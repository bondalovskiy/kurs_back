package com.bndlvsk.spotapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.bndlvsk.spotapp.model.spotify.Track;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class TracksDTO {

    @JsonProperty("items")
    private List<Track> tracks;

    private String total;

    private String range;

    private LocalDate date;

    private LocalDate lastVisit;

    public TracksDTO withRange(String range) {
        this.range = range;
        return this;
    }

    public TracksDTO withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TracksDTO withLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
        return this;
    }
}