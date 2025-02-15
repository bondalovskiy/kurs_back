package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.ArtistsDTO;
import com.bndlvsk.spotapp.DTO.GenresDTO;
import com.bndlvsk.spotapp.DTO.TracksDTO;
import org.springframework.data.util.Pair;

import java.util.Map;

public interface StatsService {

    TracksDTO getUserTracks(long userId, String range);

    ArtistsDTO getUserArtists(long userId, String range);

    void saveUserTracks(Map<TracksDTO, Long> dtoMap);

    void saveUserArtists(Map<ArtistsDTO, Long> dtoMap);

    void saveUserGenres(Map<GenresDTO, Long> dtoMap);

    void saveUserStats(long userId, Map<String, Double> statsMap);

    TracksDTO compareTracks(long userId, TracksDTO dto);

    ArtistsDTO compareArtists(long userId, ArtistsDTO dto);

    GenresDTO compareGenres(long userId, GenresDTO dto);

    Pair<Integer, Integer> matchTracks(long userId, long matchUserId);

    Pair<Integer, Integer> matchArtists(long userId, long matchUserId);

    Pair<Integer, Integer> matchGenres(long userId, long matchUserId);

    boolean isBattlePossible(long userId, long battleUserId);

    boolean isTrackSynchronized(long userId);

    boolean isArtistSynchronized(long userId);

    boolean isGenreSynchronized(long userId);
}
