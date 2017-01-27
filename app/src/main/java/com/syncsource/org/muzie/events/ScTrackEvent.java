package com.syncsource.org.muzie.events;

import com.syncsource.org.muzie.model.MostTrackItem;
import com.syncsource.org.muzie.model.ScTrackContent;

import java.util.List;

/**
 * Created by nls on 1/20/17.
 */

public class ScTrackEvent {

    public static class OnTopTrackEvent {
        private boolean success;
        private ScTrackContent item;

        public static class Builder {
            public boolean success;
            public ScTrackContent item;

            public Builder isSuccess(boolean success) {
                this.success = success;
                return this;
            }

            public Builder setItem(ScTrackContent item) {
                this.item = item;
                return this;
            }

            public OnTopTrackEvent Build() {
                return new OnTopTrackEvent(this);
            }
        }

        public OnTopTrackEvent(Builder builder) {
            this.success = builder.success;
            this.item = builder.item;
        }

        public boolean isSuccess() {
            return success;
        }

        public ScTrackContent getItem() {
            return item;
        }
    }

    public static class OnTopGenresTrackEvent {
        private boolean success;
        private ScTrackContent item;

        public static class Builder {
            public boolean success;
            public ScTrackContent item;

            public Builder isSuccess(boolean success) {
                this.success = success;
                return this;
            }

            public Builder setItem(ScTrackContent item) {
                this.item = item;
                return this;
            }

            public OnTopGenresTrackEvent Build() {
                return new OnTopGenresTrackEvent(this);
            }
        }

        public OnTopGenresTrackEvent(Builder builder) {
            this.success = builder.success;
            this.item = builder.item;
        }

        public boolean isSuccess() {
            return success;
        }

        public ScTrackContent getItem() {
            return item;
        }
    }

    public static class OnNewHotTrackEvent {
        private boolean success;
        private ScTrackContent item;

        public static class Builder {
            public boolean success;
            public ScTrackContent item;

            public Builder isSuccess(boolean success) {
                this.success = success;
                return this;
            }

            public Builder setItem(ScTrackContent item) {
                this.item = item;
                return this;
            }

            public OnNewHotTrackEvent Build() {
                return new OnNewHotTrackEvent(this);
            }
        }

        public OnNewHotTrackEvent(Builder builder) {
            this.success = builder.success;
            this.item = builder.item;
        }

        public boolean isSuccess() {
            return success;
        }

        public ScTrackContent getItem() {
            return item;
        }
    }
}
