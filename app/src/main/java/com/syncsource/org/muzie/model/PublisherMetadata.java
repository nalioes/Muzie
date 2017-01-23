package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nls on 1/23/17.
 */

public class PublisherMetadata {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("urn")
    @Expose
    private String urn;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("album_title")
    @Expose
    private String albumTitle;
    @SerializedName("contains_music")
    @Expose
    private Boolean containsMusic;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("iswc")
    @Expose
    private String iswc;
    @SerializedName("upc_or_ean")
    @Expose
    private String upcOrEan;
    @SerializedName("isrc")
    @Expose
    private String isrc;
    @SerializedName("p_line")
    @Expose
    private String pLine;
    @SerializedName("p_line_for_display")
    @Expose
    private String pLineForDisplay;
    @SerializedName("writer_composer")
    @Expose
    private String writerComposer;
    @SerializedName("release_title")
    @Expose
    private String releaseTitle;

    public Integer getId() {
        return id;
    }

    public String getUrn() {
        return urn;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public Boolean getContainsMusic() {
        return containsMusic;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIswc() {
        return iswc;
    }

    public String getUpcOrEan() {
        return upcOrEan;
    }

    public String getIsrc() {
        return isrc;
    }

    public String getpLine() {
        return pLine;
    }

    public String getpLineForDisplay() {
        return pLineForDisplay;
    }

    public String getWriterComposer() {
        return writerComposer;
    }

    public String getReleaseTitle() {
        return releaseTitle;
    }
}
