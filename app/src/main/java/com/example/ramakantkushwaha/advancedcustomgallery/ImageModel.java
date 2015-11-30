package com.example.ramakantkushwaha.advancedcustomgallery;

/**
 * Created by ramakant.kushwaha on 10/28/2015.
 */
public class ImageModel {
    boolean selected;
    String uri;


    public void setIsSelected(boolean isSelected) {
        this.selected = isSelected;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getUri() {
        return uri;
    }
}

