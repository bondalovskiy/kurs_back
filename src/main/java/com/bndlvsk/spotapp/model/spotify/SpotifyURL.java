package com.bndlvsk.spotapp.model.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SpotifyURL {

    @JsonProperty("spotify")
    private String url;
}
