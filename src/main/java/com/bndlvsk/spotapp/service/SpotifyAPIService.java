package com.bndlvsk.spotapp.service;

import com.bndlvsk.spotapp.DTO.*;
import com.bndlvsk.spotapp.model.spotify.PlaylistInfo;
import com.bndlvsk.spotapp.model.spotify.response.ResponsePlaylists;
import com.bndlvsk.spotapp.model.spotify.response.ResponseTracksAnalysis;
import org.springframework.lang.Nullable;

public interface SpotifyAPIService {

    TracksDTO getTopTracks(long userId, String range);

    ArtistsDTO getTopArtists(long userId, String range);

    RecentlyPlayedDTO getRecentlyPlayed();

    ResponseTracksAnalysis getTracksAnalysis(TracksDTO tracksDTO);

    ResponsePlaylists getUserPlaylists(@Nullable String username);

    TracksDTO getPlaylistTracks(PlaylistInfo playlistInfo, String country);

    UserDTO getCurrentUser();

    PlaylistDTO postTopTracksPlaylist(long userId, String range);

}
