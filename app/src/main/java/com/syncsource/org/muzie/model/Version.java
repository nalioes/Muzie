package com.syncsource.org.muzie.model;

/**
 * Created by nls on 4/4/17.
 */

public class Version {
    private String versionCode;
    private String versionName;

    public Version() {
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
