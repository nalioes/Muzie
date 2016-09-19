package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SyncSource on 9/4/2016.
 */
public class Thumbnails {
    @SerializedName("default")
    @Expose
    private Default _default;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("high")
    @Expose
    private High high;

    public Thumbnails() {
    }

    public Thumbnails(Default _default, Medium medium, High high) {
        this._default = _default;
        this.medium = medium;
        this.high = high;
    }

    public Default get_default() {
        return _default;
    }

    public Medium getMedium() {
        return medium;
    }

    public High getHigh() {
        return high;
    }
}
