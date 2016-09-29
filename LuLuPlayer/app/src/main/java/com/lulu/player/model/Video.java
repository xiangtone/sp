package com.lulu.player.model;

import com.google.gson.annotations.SerializedName;

/**
 * 影片信息
 *
 * @author Administrator
 * @time 2016/9/23 0023下午 4:35
 */
public class Video {

    @SerializedName("id")
    private int id;

    @SerializedName("imgs")
    private String imgUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String videoUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
