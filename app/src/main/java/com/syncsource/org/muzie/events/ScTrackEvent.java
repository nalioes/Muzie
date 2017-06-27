package com.syncsource.org.muzie.events;

import com.syncsource.org.muzie.model.ScTrackContent;

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

    public static class OnNewHotGeneresEvent {
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

            public OnNewHotGeneresEvent Build() {
                return new OnNewHotGeneresEvent(this);
            }
        }

        public OnNewHotGeneresEvent(Builder builder) {
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

    public static class OnTopGeneresEvent {
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

            public OnTopGeneresEvent Build() {
                return new OnTopGeneresEvent(this);
            }
        }

        public OnTopGeneresEvent(Builder builder) {
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
