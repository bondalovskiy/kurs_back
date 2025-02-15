package com.bndlvsk.spotapp.model.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

@JsonPropertyOrder({"genres", "images", "name", "url"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    private List<String> genres;

    private List<Image> images;

    private String name;

    private String uri;

    @JsonProperty("external_urls")
    private SpotifyURL url;

    private Integer difference;

    public Artist(String name) {
        this.name = name;
    }
}
