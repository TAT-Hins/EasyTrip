package com.seu.cose.xutils3.pojo;

public class Relationship {
	public static final String FOLLOW="follow";
	public static final String COLLECT="collect";
	public static final String AUTHOR="author";
	public static final String RECORD="record";
	
    private Integer id;

    private Integer userId;

    private Integer otherSideId;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOtherSideId() {
        return otherSideId;
    }

    public void setOtherSideId(Integer otherSideId) {
        this.otherSideId = otherSideId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}