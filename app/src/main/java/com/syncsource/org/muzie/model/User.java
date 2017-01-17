package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nalioes on 1/14/17.
 */

public class User {
    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("permalink_url")
    @Expose
    public String permalinkUrl;
    @SerializedName("uri")
    @Expose
    public String uri;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("permalink")
    @Expose
    public String permalink;
    @SerializedName("last_modified")
    @Expose
    public String lastModified;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public String getUri() {
        return uri;
    }

    public String getUsername() {
        return username;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getLastModified() {
        return lastModified;
    }
}
