package com.seu.cose.xutils3.pojo;

/**
 * Created by Hins on 2017/9/11,011.
 */

public class ImageCardView {

    private int img;
    private String keyword;

    public ImageCardView(int img, String kwd){
        this.img = img;
        this.keyword = kwd;
    }

    public int getImg(){return img;}

    public void setImg(int img){this.img = img;}

    public String getKeyword(){return keyword;}

    public void setKeyword(String kwd){keyword = kwd;}

}
