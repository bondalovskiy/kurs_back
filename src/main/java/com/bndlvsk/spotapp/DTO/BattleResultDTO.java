package com.bndlvsk.spotapp.DTO;

import com.bndlvsk.spotapp.model.spotify.Battler;
import lombok.Value;

@Value
public class BattleResultDTO {
    ProfileDTO winnerProfile;
    ProfileDTO loserProfile;
    Battler winner;
    Battler loser;
    double result;
}
