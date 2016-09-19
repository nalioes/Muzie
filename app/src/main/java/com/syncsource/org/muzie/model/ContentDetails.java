package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SyncSource on 9/4/2016.
 */
public class ContentDetails {
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("dimension")
    @Expose
    private String dimension;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("licensedContent")
    @Expose
    private Boolean licensedContent;
    @SerializedName("projection")
    @Expose
    private String projection;

    public ContentDetails() {
    }

    public ContentDetails(String duration, String dimension, String definition, String caption, Boolean licensedContent, String projection) {
        this.duration = duration;
        this.dimension = dimension;
        this.definition = definition;
        this.caption = caption;
        this.licensedContent = licensedContent;
        this.projection = projection;
    }

    public String getDuration() {
        return duration;
    }

    public String getDimension() {
        return dimension;
    }

    public String getDefinition() {
        return definition;
    }

    public String getCaption() {
        return caption;
    }

    public Boolean getLicensedContent() {
        return licensedContent;
    }

    public String getProjection() {
        return projection;
    }
}
