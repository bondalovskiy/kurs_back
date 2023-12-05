package com.bndlvsk.spotapp.service.impl;

import com.bndlvsk.spotapp.DTO.ArtistsDTO;
import com.bndlvsk.spotapp.DTO.GenresDTO;
import com.bndlvsk.spotapp.DTO.TracksDTO;
import com.bndlvsk.spotapp.service.LibraryAnalysisService;
import com.bndlvsk.spotapp.service.LibraryDataSyncService;
import com.bndlvsk.spotapp.service.SpotifyAPIService;
import com.bndlvsk.spotapp.service.StatsService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
public class InitialLibraryDataSyncService implements LibraryDataSyncService {

    private final SpotifyAPIService spotifyAPIService;
    private final LibraryAnalysisService libraryAnalysisService;
    private final StatsService statsService;

    @Override
    public void synchronizeTracks(long userId) {
        TracksDTO tracksDTO = spotifyAPIService.getTopTracks(userId, "long");
        statsService.saveUserTracks(Map.of(tracksDTO.withDate(LocalDate
                .now()
                .minusDays(1L)), userId));
    }

    @Override
    public void synchronizeArtists(long userId) {
        ArtistsDTO artistsDTO = spotifyAPIService.getTopArtists(userId, "long");
        statsService.saveUserArtists(Map.of(artistsDTO.withDate(LocalDate
                .now()
                .minusDays(1L)), userId));
    }

    @Override
    public void synchronizeGenres(long userId) {
        ArtistsDTO artistsDTO = spotifyAPIService.getTopArtists(userId, "long");
        GenresDTO genresDTO = libraryAnalysisService.getTopGenres(userId, "long", artistsDTO);
        statsService.saveUserGenres(Map.of(genresDTO.withDate(LocalDate
                .now()
                .minusDays(1L)), userId));
    }

    @Override
    public boolean isLibraryDataSynchronized(long userId) {
        if (!statsService.isTrackSynchronized(userId)) return false;
        if (!statsService.isArtistSynchronized(userId)) return false;
        return statsService.isGenreSynchronized(userId);
    }
}
