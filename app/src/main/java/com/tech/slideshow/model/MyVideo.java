package com.tech.slideshow.model;

public class MyVideo {
    private String absolutePath;
    private String lastModified;
    private Long length;
    private String name;

    public MyVideo() {
    }

    public MyVideo(String absolutePath, String lastModified, Long length, String name) {
        this.absolutePath = absolutePath;
        this.lastModified = lastModified;
        this.length = length;
        this.name = name;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
