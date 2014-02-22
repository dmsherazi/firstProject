package com.astrolabe.iremote;

/**
 * Created by Dost on 1/16/14.
 * for Kindows Tech Solutions
 */public class Model {

    private String name;
    private boolean selected;

    public Model(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
