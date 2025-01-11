package com.tech.slideshow.model;

public class ShareModel {
    int idResource;
    int idName;

    public ShareModel(int idResource, int idName) {
        this.idResource = idResource;
        this.idName = idName;
    }

    public int getIdResource() {
        return idResource;
    }

    public void setIdResource(int idResource) {
        this.idResource = idResource;
    }

    public int getIdName() {
        return idName;
    }

    public void setIdName(int idName) {
        this.idName = idName;
    }
}
