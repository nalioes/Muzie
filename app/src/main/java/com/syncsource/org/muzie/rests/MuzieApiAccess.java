package com.syncsource.org.muzie.rests;

/**
 * Created by SyncSource on 9/1/2016.
 */
public interface MuzieApiAccess {

    void getTrackID(String part, String query, String maxNumber, String key);

    void getNextTrackID(String token, String part, String query, String maxNumber, String key);

    void getTrackDuration(String part, String id, String key);

    void getNextTrackDuration(String part, String id, String key);

    void getLatestTrack(String part, String afterAt, String beforeAt, String key);

    void getLatestTrackDuration(String part, String id, String key);

    void getNextLatestTrack(String token, String part, String afterAt, String beforeAt, String key);

    void getNextLatestTrackDuration(String part, String id, String key);

    void getRelatedTrackID(String part, String relatedVideoId, String maxNumber, String key);

    void getNextRelatedTrackID(String token, String part, String videoId, String maxNumber, String key);

}
