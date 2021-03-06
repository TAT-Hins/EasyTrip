package com.seu.cose.easytrip.Override;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.pojo.ImageCardView;

import java.util.List;

/**
 * Created by Hins on 2017/9/11,011.
 */

public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.WaterFallViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ImageCardView> imageBeen;

    public WaterFallAdapter(Context context, List<ImageCardView> list){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        imageBeen = list;
    }

    @Override
    public WaterFallViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = mInflater.inflate(R.layout.cardview_community, viewGroup, false);
        return new WaterFallViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WaterFallViewHolder holder, int position){
        holder.image.setImageURI(Uri.parse(imageBeen.get(position).getImg()));
        holder.keyword.setText(imageBeen.get(position).getKeyword());
    }

    @Override
    public int getItemCount(){
        return imageBeen.size();
    }


    public static class WaterFallViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView keyword;

        public WaterFallViewHolder(View itemView){
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.card_community_image);
            keyword = (TextView) itemView.findViewById(R.id.card_community_keyword);
        }

    }

    //添加数据
    public void addItem(List<ImageCardView> newCards) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newCards.addAll(imageBeen);
        imageBeen.removeAll(imageBeen);
        imageBeen.addAll(newCards);
        notifyDataSetChanged();
    }

}
