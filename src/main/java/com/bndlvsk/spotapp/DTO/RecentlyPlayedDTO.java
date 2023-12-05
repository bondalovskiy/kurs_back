package com.bndlvsk.spotapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.bndlvsk.spotapp.model.spotify.PlaybackEvent;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RecentlyPlayedDTO {

    String total;

    @JsonProperty("items")
    List<PlaybackEvent> playbackEvents;
}
