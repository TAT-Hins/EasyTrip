package com.seu.cose.xutils3.pojo;

/**
 * Created by Hins on 2017/9/11,011.
 */

public class Relationship {
    private Integer userId;

    private Integer otherSideId;

    private Byte type;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}