package com.bndlvsk.spotapp.model.mapbox;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Coordinates {
    Double longitude;
    Double latitude;
}
