package com.bndlvsk.spotapp.DTO;

import com.bndlvsk.spotapp.model.spotify.Image;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class AnalysisDTO {
    Map<String, Double> analysis;
    List<Image> images;
}


