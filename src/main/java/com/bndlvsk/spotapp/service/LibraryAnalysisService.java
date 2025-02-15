package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.*;
import java.util.Map;

public interface LibraryAnalysisService {

    AnalysisDTO getTracksAnalysis(TracksDTO tracksDTO, Long userId);

    GenresDTO getTopGenres(long userId, String range, ArtistsDTO artistsDTO);

    Map<String, Double> getUsersMatching(long userId, long matchUserId);

    BattleResultDTO createPlaylistBattle(long userId,
                                         long battleUserId,
                                         TracksDTO playlist,
                                         TracksDTO playlistToBattle);
}
