package com.syncsource.org.muzie.events;

import com.syncsource.org.muzie.model.Item;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.TrackItem;

import java.util.List;

/**
 * Created by SyncSource on 9/1/2016.
 */
public class TrackEvent {

    public static class OnLatestSnippetEvent {
        private boolean success;
        private List<Item> item;
        private String token;

        public OnLatestSnippetEvent(boolean success) {
            this.success = success;
        }

        public OnLatestSnippetEvent(boolean success, List<Item> item,String token) {
            this.success = success;
            this.item = item;
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public List<Item> getItem() {
            return item;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class OnSnippetEvent {
        private boolean success;
        private List<Item> item;
        private String token;

        public OnSnippetEvent(boolean success) {
            this.success = success;
        }

        public OnSnippetEvent(boolean success, List<Item> item,String token) {
            this.success = success;
            this.item = item;
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public List<Item> getItem() {
            return item;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class OnSnippetTrackEvent {
        private boolean success;
        private List<Item> item;
        private String token;

        public OnSnippetTrackEvent(boolean success) {
            this.success = success;
        }

        public OnSnippetTrackEvent(boolean success, List<Item> item,String token) {
            this.success = success;
            this.item = item;
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public List<Item> getItem() {
            return item;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class OnLatestTrackIDEvent {
        private boolean success;
        private List<TrackItem> item;

        public OnLatestTrackIDEvent(boolean success) {
            this.success = success;
        }

        public OnLatestTrackIDEvent(boolean success, List<TrackItem> item) {
            this.success = success;
            this.item = item;
        }

        public List<TrackItem> getItem() {
            return item;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class OnTrackIDEvent {
        private boolean success;
        private List<TrackItem> item;

        public OnTrackIDEvent(boolean success) {
            this.success = success;
        }

        public OnTrackIDEvent(boolean success, List<TrackItem> item) {
            this.success = success;
            this.item = item;
        }

        public List<TrackItem> getItem() {
            return item;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class OnNextTrackIDEvent {
        private boolean success;
        private List<TrackItem> item;

        public OnNextTrackIDEvent(boolean success) {
            this.success = success;
        }

        public OnNextTrackIDEvent(boolean success, List<TrackItem> item) {
            this.success = success;
            this.item = item;
        }

        public List<TrackItem> getItem() {
            return item;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class OnSyncEvent {
        private boolean success;
        private MyTrack myTrack;

        public OnSyncEvent(boolean success) {
            this.success = success;
        }

        public OnSyncEvent(boolean success ,MyTrack myTrack) {
            this.success = success;
            this.myTrack = myTrack;
        }

        public MyTrack getMyTrack() {
            return myTrack;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public static class OnTrackSyncEvent {
        private boolean success;
        private List<MyTrack> myTrack;

        public OnTrackSyncEvent(boolean success, List<MyTrack> myTrack) {
            this.success = success;
            this.myTrack = myTrack;
        }

        public boolean isSuccess() {
            return success;
        }

        public List<MyTrack> getMyTrack() {
            return myTrack;
        }
    }
}
