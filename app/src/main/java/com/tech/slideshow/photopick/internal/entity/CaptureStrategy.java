
package com.tech.slideshow.photopick.internal.entity;

public class CaptureStrategy {
    public final boolean isPublic;
    public final String authority;
    public final String directory;
    public CaptureStrategy(boolean isPublic, String authority, String directory) {
        this.isPublic = isPublic;
        this.authority = authority;
        this.directory = directory;
    }
}
