package com.tech.slideshow.model;

public class Music {
    private int resId;
    private String nameMusic;
    private int restImage;

    public Music(int resId, String nameMusic, int restImage) {
        this.resId = resId;
        this.nameMusic = nameMusic;
        this.restImage = restImage;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getNameMusic() {
        return nameMusic;
    }

    public void setNameMusic(String nameMusic) {
        this.nameMusic = nameMusic;
    }

    public int getRestImage() {
        return restImage;
    }

    public void setRestImage(int restImage) {
        this.restImage = restImage;
    }
}
