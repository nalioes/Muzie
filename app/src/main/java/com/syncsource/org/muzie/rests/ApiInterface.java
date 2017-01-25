package com.syncsource.org.muzie.rests;

import com.syncsource.org.muzie.model.MostTrackContent;
import com.syncsource.org.muzie.model.ScTrackContent;
import com.syncsource.org.muzie.model.SearchContentID;
import com.syncsource.org.muzie.model.TrackID;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by SyncSource on 9/1/2016.
 */
public interface ApiInterface {

    @GET("/youtube/v3/videos?")
    Call<TrackID> getTrackDuration(@Query("part") String part, @Query("id") String id, @Query("key") String key);

    @GET("/youtube/v3/search?")
    Call<SearchContentID> getTrackId(@Query("part") String part, @Query("q") String query, @Query("type") String type, @Query("videoCategoryId") String categoryId, @Query("maxResults") String maxNumber, @Query("key") String key);

    @GET("/youtube/v3/search?")
    Call<SearchContentID> getNextTrackId(@Query("pageToken") String token, @Query("part") String part, @Query("q") String query, @Query("type") String type, @Query("videoCategoryId") String categoryId, @Query("maxResults") String maxNumber, @Query("key") String key);

    @GET("/youtube/v3/videos?")
    Call<MostTrackContent> getLatestTrack(@Query("part") String part, @Query("type") String type, @Query("videoCategoryId") String categoryId, @Query("maxResults") String maxNumber, @Query("chart") String chart, @Query("publishedAfter") String publishedAfter, @Query("publishedBefore") String publishedBefore, @Query("key") String key);

    @GET("/youtube/v3/videos?")
    Call<MostTrackContent> getNextLatestTrack(@Query("pageToken") String token, @Query("part") String part, @Query("type") String type, @Query("videoCategoryId") String categoryId, @Query("maxResults") String maxNumber, @Query("chart") String chart, @Query("publishedAfter") String publishedAfter, @Query("publishedBefore") String publishedBefore, @Query("key") String key);

    @GET("/youtube/v3/search?")
    Call<SearchContentID> getRelatedTrackId(@Query("part") String part, @Query("type") String type, @Query("relatedToVideoId") String relatedToVideoId, @Query("maxResults") String maxNumber, @Query("key") String key);

    @GET("/youtube/v3/search?")
    Call<SearchContentID> getNextRelatedTrackId(@Query("pageToken") String token, @Query("part") String part, @Query("type") String type, @Query("relatedToVideoId") String relatedToVideoId, @Query("maxResults") String maxNumber, @Query("key") String key);

    @GET("/tracks?")
    Call<List<ScTrackContent>> getSearchScTrack(@Query("q") String query, @Query("limit") String limit, @Query("client_id") String clientId, @Query("linked_partitioning") String linked_partitioning);

    @GET("/tracks?")
    Call<List<ScTrackContent>> getNextSearchScTrack(@Query("q") String query, @Query("limit") String limit, @Query("client_id") String clientId, @Query("linked_partitioning") String linked_partitioning, @Query("next_href") String next_href);


    @GET("/tracks/{track_id}/related?")
    Call<List<ScTrackContent>> getRelatedScTrack(@Path("track_id") String track_id, @Query("limit") String limit, @Query("client_id") String clientId, @Query("linked_partitioning") String linked_partitioning);


    @GET("/charts?")
    Call<List<ScTrackContent>> getTopScTrack(@Query("kind") String kind, @Query("limit") String limit, @Query("client_id") String clientId, @Query("linked_partitioning") String linked_partitioning);

    @GET("/charts?")
    Call<List<ScTrackContent>> getNextTopScTrack(@Query("kind") String kind, @Query("limit") String limit, @Query("client_id") String clientId, @Query("linked_partitioning") String linked_partitioning, @Query("next_href") String next_href);


    @GET("/charts?")
    Call<ScTrackContent> getTopGenreScTrack(@Query("kind") String kind, @Query("genre") String genre, @Query("limit") String limit, @Query("client_id") String clientId, @Query("linked_partitioning") String linked_partitioning);

    @GET("/charts?")
    Call<List<ScTrackContent>> getNextTopGenreScTrack(@Query("kind") String kind, @Query("genre") String genre, @Query("limit") String limit, @Query("client_id") String clientId, @Query("linked_partitioning") String linked_partitioning, @Query("next_href") String next_href);

}
