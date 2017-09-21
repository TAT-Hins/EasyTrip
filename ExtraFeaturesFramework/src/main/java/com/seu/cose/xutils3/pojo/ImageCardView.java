package com.seu.cose.xutils3.pojo;

/**
 * Created by Hins on 2017/9/11,011.
 */

public class ImageCardView {

    private String img;
    private String keyword;

    public ImageCardView(String img, String kwd){
        this.img = img;
        this.keyword = kwd;
    }

    public String getImg(){return img;}

    public void setImg(String img){this.img = img;}

    public String getKeyword(){return keyword;}

    public void setKeyword(String kwd){keyword = kwd;}

}
