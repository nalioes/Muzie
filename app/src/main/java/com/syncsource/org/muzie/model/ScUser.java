package com.syncsource.org.muzie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nls on 1/23/17.
 */

public class ScUser {
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("last_modified")
    @Expose
    private String lastModified;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("permalink_url")
    @Expose
    private String permalinkUrl;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("urn")
    @Expose
    private String urn;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country_code")
    @Expose
    private String countryCode;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public String getUri() {
        return uri;
    }

    public String getUrn() {
        return urn;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getVerified() {
        return verified;
    }

    public String getCity() {
        return city;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
