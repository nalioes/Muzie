package com.syncsource.org.muzie.rests;

import com.syncsource.org.muzie.model.MostTrackContent;
import com.syncsource.org.muzie.model.SearchContentID;
import com.syncsource.org.muzie.model.TrackID;

import retrofit2.Call;
import retrofit2.http.GET;
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
    Call<SearchContentID> getNextTrackId(@Query("pageToken") String token,@Query("part") String part, @Query("q") String query, @Query("type") String type, @Query("videoCategoryId") String categoryId, @Query("maxResults") String maxNumber, @Query("key") String key);

    @GET("/youtube/v3/videos?")
    Call<MostTrackContent> getLatestTrack(@Query("part") String part, @Query("type") String type, @Query("videoCategoryId") String categoryId, @Query("maxResults") String maxNumber, @Query("chart") String chart, @Query("publishedAfter") String publishedAfter, @Query("publishedBefore") String publishedBefore, @Query("key") String key);

    @GET("/youtube/v3/videos?")
    Call<MostTrackContent> getNextLatestTrack(@Query("pageToken") String token,@Query("part") String part,@Query("type") String type, @Query("videoCategoryId") String categoryId, @Query("maxResults") String maxNumber,@Query("chart") String chart,@Query("publishedAfter") String publishedAfter,@Query("publishedBefore") String publishedBefore, @Query("key") String key);

}
