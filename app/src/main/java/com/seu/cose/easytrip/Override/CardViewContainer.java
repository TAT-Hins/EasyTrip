package com.seu.cose.easytrip.Override;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Hins on 2017/9/8,008.
 */

public class CardViewContainer {

    public String title, intro;
    public String imageUrl;

    public CardViewContainer(String mTitle, String mIntro, String mImageUrl){
        this.title = mTitle;
        this.intro = mIntro;
        this.imageUrl = mImageUrl;
    }

    public String getImageResourceId(Context context){

        try{
            return imageUrl;
            //return context.getResources().getIdentifier(this.imageId, "drawable", context.getPackageName());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
