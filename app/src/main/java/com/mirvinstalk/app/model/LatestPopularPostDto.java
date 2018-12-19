package com.mirvinstalk.app.model;

import android.support.annotation.Nullable;

import java.io.Serializable;

public class LatestPopularPostDto implements Serializable {
    private Integer LikeCount;
    private String Url;
    private Long TimeTaken;
    private String Caption;
    private Integer CommentCount;
    private Integer Height;
    private Integer Width;

    public Integer getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(Integer likeCount) {
        LikeCount = likeCount;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Long getTimeTaken() {
        return TimeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        TimeTaken = timeTaken;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public Integer getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(Integer commentCount) {
        CommentCount = commentCount;
    }

    public Integer getHeight() {
        return Height;
    }

    public void setHeight(Integer height) {
        Height = height;
    }

    public Integer getWidth() {
        return Width;
    }

    public void setWidth(Integer width) {
        Width = width;
    }
}
