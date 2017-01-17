package com.syncsource.org.muzie.utils;

import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import com.syncsource.org.muzie.model.Item;
import com.syncsource.org.muzie.model.MostTrackItem;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.TrackItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

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
                            myTrack.setViewCount(convertViewCount(trackItems.get(i).getStatistics().getViewCount()) + " views");
                            myTrack.setDuration(convertDuration(trackItems.get(i).getContentDetails().getDuration()));
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

    public List<MyTrack> getMostPopuTrackList(List<MostTrackItem> snippetItems, List<TrackItem> trackItems, String token) {
        if (trackItems.size() > 0) {
            myTracks = new ArrayList<>();

            for (int i = 0; i < trackItems.size(); i++) {
                for (int j = 0; j < snippetItems.size(); j++) {
                    if (!TextUtils.isEmpty(snippetItems.get(j).getId())) {
                        if (trackItems.get(i).getId().toString().compareToIgnoreCase(snippetItems.get(j).getId().toString()) == 0) {
                            MyTrack myTrack = new MyTrack();
                            myTrack.setVideoID(trackItems.get(i).getId());
                            myTrack.setViewCount(convertViewCount(trackItems.get(i).getStatistics().getViewCount()) + " views");
                            myTrack.setDuration(convertDuration(trackItems.get(i).getContentDetails().getDuration()));
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

    public static String convertDuration(String duration) {
        String durationTime = duration;
        Calendar c = new GregorianCalendar();
        try {
            DateFormat df = new SimpleDateFormat("'PT'mm'M'ss'S'");
            Date d = df.parse(durationTime);
            c.setTime(d);
        } catch (ParseException e) {
            try {
                DateFormat df = new SimpleDateFormat("'PT'hh'H'mm'M'ss'S'");
                Date d = df.parse(durationTime);
                c.setTime(d);
            } catch (ParseException e1) {
                try {
                    DateFormat df = new SimpleDateFormat("'PT'ss'S'");
                    Date d = df.parse(durationTime);
                    c.setTime(d);
                } catch (ParseException e2) {
                }
            }
        }
        c.setTimeZone(TimeZone.getDefault());

        String time = "";
        if (c.get(Calendar.HOUR) > 0) {
            if (String.valueOf(c.get(Calendar.HOUR)).length() == 1) {
                time += "0" + c.get(Calendar.HOUR);
            } else {
                time += c.get(Calendar.HOUR);
            }
            time += ":";
        }
        if (String.valueOf(c.get(Calendar.MINUTE)).length() == 1) {
            time += "0" + c.get(Calendar.MINUTE);
        } else {
            time += c.get(Calendar.MINUTE);
        }
        time += ":";
        if (String.valueOf(c.get(Calendar.SECOND)).length() == 1) {
            time += "0" + c.get(Calendar.SECOND);
        } else {
            time += c.get(Calendar.SECOND);
        }
        return time;
    }

    public static String convertViewCount(String count) {
        long viewCount = Long.parseLong(count);
        if (viewCount < 1000) {
            return String.valueOf(viewCount);
        }
        int exp = (int) (Math.log(viewCount) / Math.log(1000));
        return String.format("%.1f%c", viewCount / Math.pow(1000, exp), "kMGTPE".charAt(exp - 1));
    }

    public static String convertToSCDuration(long ms) {
        long seconds = ms / 1000;
        long minutes = seconds / 60;
        return minutes % 60 < 10 ? "0" + minutes % 60 + ":" + seconds % 60 : minutes % 60 + ":" + seconds % 60;
    }

    public static String getScCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(calendar.getTime());
        return date;
    }

    public static String getScPreviousTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(calendar.getTime());
        return date;
    }
}
