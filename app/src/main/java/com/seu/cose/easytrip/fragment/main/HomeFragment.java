package com.seu.cose.easytrip.fragment.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seu.cose.easytrip.Override.CardViewContainer;
import com.seu.cose.easytrip.Override.MyPageAdapter;
import com.seu.cose.easytrip.Override.RecyclerViewAdapter;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseAppFragment {

    @ViewInject(R.id.bannerViewPager)
        private ViewPager bannerViewPager;
    @ViewInject(R.id.point_group)
        private LinearLayout point_group;
    @ViewInject(R.id.image_desc)
        private TextView image_desc;
    @ViewInject(R.id.recyclerView_home)
        private RecyclerView mRecyclerView;

    private int lastVisibleItem;
    private static final String TAG = "HomeFragment";

    private List<CardViewContainer> cards = new ArrayList<>();

    private int[] mImageId = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private int[] mCardImageId = new int[]{R.drawable.card_a, R.drawable.card_b, R.drawable.card_c, R.drawable.card_d, R.drawable.card_e};
    private String[] imageDes = new String[]{"第一张", "第二张", "第三张", "第四张", "第五张"};
    private String[] mCardIntro = new String[]{"李时珍的皮", "今年国庆放8天", "没想到吧", "嘻嘻，SB", "你老婆正吃辣条呢"};
    private RecyclerViewAdapter recyclerViewAdapter;

    private ArrayList<ImageView> imageList;
    // 上一个页面的位置
    protected int lastPosition = 0;

    // 判断是否自动滚动viewPager
    private boolean isRunning = true;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 执行滑动到下一个页面
            bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
            if (isRunning) {
                // 在发一个handler延时
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated -> MainActivity窗体创建Fragment实例……");
        // 获取数据并装载到组件中
        /**/
        image_desc.setText(imageDes[0]);

        for (int i=0; i<5; i++){
            cards.add(new CardViewContainer((imageDes[i]), mCardIntro[i], mCardImageId[i]));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new RecyclerViewAdapter(this.getActivity(), cards);
        mRecyclerView.setAdapter(recyclerViewAdapter);


        // 初始化图片资源
        imageList = new ArrayList<>();
        for (int i : mImageId) {
            // 初始化图片资源
            ImageView imageView = new ImageView(this.getContext());
            imageView.setBackgroundResource(i);
            imageList.add(imageView);

            // 添加指示小点
            ImageView point = new ImageView(this.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            params.bottomMargin = 10;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.ic_grey_circle);
            if (i == R.mipmap.a) {
                //默认聚焦在第一张
                point.setBackgroundResource(R.drawable.ic_red_circle);
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }

            point_group.addView(point);
        }

        bannerViewPager.setAdapter(new MyPageAdapter(imageList));
        // 设置当前viewPager的位置
        bannerViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageList.size()));
        bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // 页面切换后调用， position是新的页面位置

                // 实现无限制循环播放
                position %= imageList.size();

                image_desc.setText(imageDes[position]);

                // 把当前点设置为true,将上一个点设为false；并设置point_group图标
                point_group.getChildAt(position).setEnabled(true);
                point_group.getChildAt(position).setBackgroundResource(R.drawable.ic_red_circle);//设置聚焦时的图标样式
                point_group.getChildAt(lastPosition).setEnabled(false);
                point_group.getChildAt(lastPosition).setBackgroundResource(R.drawable.ic_grey_circle);//上一张恢复原有图标
                lastPosition = position;

            }

            @Override // 页面正在滑动时间回调
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override // 当pageView 状态发生改变的时候，回调
            public void onPageScrollStateChanged(int state) {}

        });



        /*
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == recyclerViewAdapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<CardViewContainer> newCards = new ArrayList<>();
                            for (int i = 0; i< 2; i++) {
                                Integer id = mImageId[i];
                                newCards.add(new CardViewContainer((imageDes[i]), mCardIntro[i], id.toString()));
                            }
                            recyclerViewAdapter.addMoreItem(newCards);
                        }
                    },1000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        */



        /**
         * 自动循环： 1.定时器：Timer 2.开子线程：while true循环 3.ClockManger
         * 4.用Handler发送延时信息，实现循环，最简单最方便
         *
         */

        handler.sendEmptyMessageDelayed(0, 5000);

    }



}
