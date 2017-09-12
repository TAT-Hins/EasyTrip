package com.seu.cose.easytrip.Override;

import android.content.Context;

/**
 * Created by Hins on 2017/9/8,008.
 */

public class CardViewContainer {

    String title, intro;
    int imageId;

    public CardViewContainer(String mTitle, String mIntro, int mImageId){
        this.title = mTitle;
        this.intro = mIntro;
        this.imageId = mImageId;
    }

    public int getImageResourceId(Context context){

        try{
            return imageId;
            //return context.getResources().getIdentifier(this.imageId, "drawable", context.getPackageName());
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }

    }

}
