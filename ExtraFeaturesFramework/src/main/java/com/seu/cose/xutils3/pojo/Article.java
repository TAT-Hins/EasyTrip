package com.seu.cose.xutils3.pojo;

import java.util.Date;

public class Article {
    private Integer id;

    private Integer authorId;

    private String publishTime;

    private String title;

    private String contentUrl;

    private Integer hot;

    private String previewUrl;

    private Byte toUserLabel;

    private String placeLabel;

    private Byte aimLabel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl == null ? null : contentUrl.trim();
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl == null ? null : previewUrl.trim();
    }

    public Byte getToUserLabel() {
        return toUserLabel;
    }

    public void setToUserLabel(Byte toUserLabel) {
        this.toUserLabel = toUserLabel;
    }

    public String getPlaceLabel() {
        return placeLabel;
    }

    public void setPlaceLabel(String placeLabel) {
        this.placeLabel = placeLabel == null ? null : placeLabel.trim();
    }

    public Byte getAimLabel() {
        return aimLabel;
    }

    public void setAimLabel(Byte aimLabel) {
        this.aimLabel = aimLabel;
    }
}