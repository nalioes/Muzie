package com.syncsource.org.muzie.model;

/**
 * Created by nls on 6/14/17.
 */

public class Filter {
    private String name;
    private String value;

    public Filter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
