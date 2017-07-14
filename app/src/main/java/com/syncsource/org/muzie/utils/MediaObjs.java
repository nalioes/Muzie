package com.syncsource.org.muzie.utils;

import com.syncsource.org.muzie.model.SCMusic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nls on 7/14/17.
 */

public class MediaObjs implements Serializable {
    private List<SCMusic> scMusics;
    private int position = 0;

    public MediaObjs(List<SCMusic> scMusics, int position) {
        this.scMusics = scMusics;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public List<SCMusic> getScMusics() {
        return scMusics;
    }
}
