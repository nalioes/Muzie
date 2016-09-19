package com.syncsource.org.muzie.utils;

import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import com.syncsource.org.muzie.model.Item;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.TrackItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SyncSource on 9/4/2016.
 */
public class TrackManageUtil {

    List<MyTrack> myTracks;

    public TrackManageUtil() {
    }

    public List<MyTrack> getTrackList(List<Item> snippetItems, List<TrackItem> trackItems, String token) {
        if (trackItems.size() > 0) {
            myTracks = new ArrayList<>();

            for (int i = 0; i < trackItems.size(); i++) {
                for (int j = 0; j < snippetItems.size(); j++) {
                    if (!TextUtils.isEmpty(snippetItems.get(j).getId().getVideoId())) {
                        if (trackItems.get(i).getId().toString().compareToIgnoreCase(snippetItems.get(j).getId().getVideoId().toString()) == 0) {
                            MyTrack myTrack = new MyTrack();
                            myTrack.setVideoID(trackItems.get(i).getId());
                            myTrack.setDuration(trackItems.get(i).getContentDetails().getDuration());
                            myTrack.setTitle(snippetItems.get(j).getSnippet().getTitle());
                            myTrack.setDescription(snippetItems.get(j).getSnippet().getDescription());
                            myTrack.setThumbnail(snippetItems.get(j).getSnippet().getThumbnails().getHigh().getUrl());
                            myTrack.setNextToken(token);
                            myTrack.setChannelTitle(snippetItems.get(i).getSnippet().getChannelTitle());
                            myTracks.add(myTrack);
                        }
                    }

                }

            }

        }
        return myTracks;
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String date = format.format(calendar.getTime());
        return date;
    }

    public static String getPreviousTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String date = format.format(calendar.getTime());
        return date;
    }
}
