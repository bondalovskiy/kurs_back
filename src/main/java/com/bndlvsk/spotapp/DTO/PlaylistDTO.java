package com.bndlvsk.spotapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.bndlvsk.spotapp.model.spotify.SpotifyURL;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PlaylistDTO {

    String id;

    @JsonProperty("external_urls")
    SpotifyURL url;

}
