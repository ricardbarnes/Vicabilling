package com.vonblum.vicabilling.app.view.helper;

public class ComboItem {

    private final String key;
    private final String value;

    public ComboItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
