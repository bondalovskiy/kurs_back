package com.bndlvsk.spotapp.model.spotify;

import com.bndlvsk.spotapp.DTO.AnalysisDTO;
import lombok.Value;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;

import java.util.Map;

@Value
public class Battler {
    long id;
    double score;
    Map<String, Double> battlerAnalysis;

    public Battler(long id, AnalysisDTO playlistAnalysis) {
        this.id = id;
        this.score = (100. - playlistAnalysis
                .getAnalysis()
                .getOrDefault("mainstream", 0.)) +
                playlistAnalysis
                        .getAnalysis()
                        .getOrDefault("boringness", 0.);
        this.battlerAnalysis = playlistAnalysis.getAnalysis();
    }

     //@return winner 1st loser 2nd

    public Pair<Battler, Battler> battle(@NonNull Battler o) {
        int compare = Double.compare(this.score, o.score);
        if (compare < 0)
            return Pair.of(o, this);
        else if (compare > 0)
            return Pair.of(this, o);
        return null;
    }

    public double getDifference(@NonNull Battler o) {
        return Math.abs(this.score - o.score);
    }
}
