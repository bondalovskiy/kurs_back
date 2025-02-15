package com.bndlvsk.spotapp.DTO;

import com.bndlvsk.spotapp.model.spotify.PlaylistInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;


@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BattleDTO {
    @NotNull
    PlaylistInfo playlist;
    @NotNull
    PlaylistInfo playlistBattle;
}
