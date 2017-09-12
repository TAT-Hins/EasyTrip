package com.seu.cose.xutils3.pojo;

/**
 * Created by Hins on 2017/9/11,011.
 */

public class UserInfo {
    private Integer id;

    private Integer age;

    private Boolean sex;

    private String address;

    private String profilePhoto;

    private Integer followeeNum;

    private Integer fanNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto == null ? null : profilePhoto.trim();
    }

    public Integer getFolloweeNum() {
        return followeeNum;
    }

    public void setFolloweeNum(Integer followeeNum) {
        this.followeeNum = followeeNum;
    }

    public Integer getFanNum() {
        return fanNum;
    }

    public void setFanNum(Integer fanNum) {
        this.fanNum = fanNum;
    }
}