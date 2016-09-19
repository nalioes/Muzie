package com.syncsource.org.muzie.model;

import java.util.List;

/**
 * Created by SyncSource on 9/5/2016.
 */
public class TrackList {
    private List<MyTrack>myTracks;

    public TrackList() {
    }

    public TrackList(List<MyTrack> myTracks) {
        this.myTracks = myTracks;
    }

    public List<MyTrack> getMyTracks() {
        return myTracks;
    }

    public void setMyTracks(List<MyTrack> myTracks) {
        this.myTracks = myTracks;
    }
}
