package com.seu.cose.easytrip.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Hins on 2017/9/8,008.
 */

import com.seu.cose.easytrip.Override.CardViewContainer;
import com.seu.cose.easytrip.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<CardViewContainer> cards;
    private Context mContext;

    public RecyclerViewAdapter( Context context , List<CardViewContainer> cards){
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.cards = cards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i ){
        // 给ViewHolder设置布局文件
        View v = mInflater.inflate(R.layout.cardview_home, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        // 给ViewHolder设置元素
        CardViewContainer p = cards.get(i);
        viewHolder.mLinearLayout.setTag(i);
        viewHolder.mTextView_title.setText(p.title);
        viewHolder.mTextView_intro.setText(p.intro);
        viewHolder.mImageView.setImageURI(Uri.parse(p.imageUrl));
    }

    @Override
    public int getItemCount(){
        // 返回数据总数
        return cards == null ? 0 : cards.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView_title;
        public TextView mTextView_intro;
        public ImageView mImageView;
        public LinearLayout mLinearLayout;

        public ViewHolder( View v ){
            super(v);
            mLinearLayout = v.findViewById(R.id.card_home);
            mTextView_title = v.findViewById(R.id.card_home_title);
            mTextView_intro = v.findViewById(R.id.card_home_intro);
            mImageView =  v.findViewById(R.id.card_home_image);
        }
    }

    //添加数据
    public void addItem(List<CardViewContainer> newCards) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newCards.addAll(cards);
        cards.removeAll(cards);
        cards.addAll(newCards);
        notifyDataSetChanged();
    }

    public void addMoreItem(List<CardViewContainer> newCards) {
        cards.addAll(newCards);
        notifyDataSetChanged();
    }

}
