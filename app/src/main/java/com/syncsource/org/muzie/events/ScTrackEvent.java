package com.syncsource.org.muzie.events;

import com.syncsource.org.muzie.model.MostTrackItem;
import com.syncsource.org.muzie.model.ScTrackContent;

import java.util.List;

/**
 * Created by nls on 1/20/17.
 */

public class ScTrackEvent {
    public static class OnMostPopularTrackEvent {
        private boolean success;
        private List<ScTrackContent> item;
        private String token;

        public OnMostPopularTrackEvent(boolean success) {
            this.success = success;
        }

        public OnMostPopularTrackEvent(boolean success, List<ScTrackContent> item, String token) {
            this.success = success;
            this.item = item;
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public List<ScTrackContent> getItem() {
            return item;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
