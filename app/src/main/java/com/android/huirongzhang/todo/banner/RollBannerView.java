package com.android.huirongzhang.todo.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.huirongzhang.todo.ActivityUtils;
import com.android.huirongzhang.todo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/6/30.
 */
public class RollBannerView extends FrameLayout implements OnPageChangeListener {

    private static final String TAG = "RollBannerView";

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mIndicatorLayout;

    private List<String> mDataList;
    private List<ImageView> mIndicatorList;
    private BannerAdapter mBannerAdapter;

    private Handler mHandler;
    private AutoRollRunnable mAutoRollRunnable;
    private BannerClickListener mBannerClickListener;

    private int mPrePosition = 0;

    public RollBannerView(Context context) {
        this(context, null);
    }

    public RollBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
    }

    private void initView() {
        View.inflate(mContext, R.layout.layout_roll_banner, this);
        mViewPager = (ViewPager) findViewById(R.id.banner_content);
        mIndicatorLayout = (LinearLayout) findViewById(R.id.banner_indicator);
    }

    private void initData() {
        mIndicatorList = new ArrayList<ImageView>();
        mAutoRollRunnable = new AutoRollRunnable();
        mHandler = new Handler();
        mBannerAdapter = new BannerAdapter();
        mViewPager.setAdapter(mBannerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
    }

    public void setData(List<String> dataList) {
        this.mDataList = dataList;
        if (mDataList != null && !mDataList.isEmpty()) {
            //清空数据
            mIndicatorList.clear();
            mIndicatorLayout.removeAllViews();
            ImageView indicatorView;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < mDataList.size(); i++) {
                indicatorView = new ImageView(mContext);
                if (i == 0) {
                    indicatorView.setBackgroundResource(R.drawable.banner_dot_select);
                } else {
                    indicatorView.setBackgroundResource(R.drawable.banner_dot_normal);
                }
                //设置点的间距
                params.setMargins(0, 0, ActivityUtils.dip2px(mContext, 5), 0);
                indicatorView.setLayoutParams(params);

                //添加点到LinearLayout上
                mIndicatorLayout.addView(indicatorView);
                //添加到集合中, 以便控制其切换
                mIndicatorList.add(indicatorView);
            }
        }
        startRoll();
    }

    public void setBannerClickListener(BannerClickListener listener) {
        this.mBannerClickListener = listener;
    }

    //开始轮播
    public void startRoll() {
        mAutoRollRunnable.start();
    }

    // 停止轮播
    public void stopRoll() {
        mAutoRollRunnable.stop();
    }

    private class AutoRollRunnable implements Runnable {

        //是否在轮播的标志
        boolean isRunning = false;

        public void start() {
            if (!isRunning) {
                isRunning = true;
                mHandler.removeCallbacks(this);
                mHandler.postDelayed(this, 2000);
            }
        }

        public void stop() {
            if (isRunning) {
                mHandler.removeCallbacks(this);
                isRunning = false;
            }
        }

        @Override
        public void run() {
            if (isRunning) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                mHandler.postDelayed(this, 2000);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled()");
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected()");
        mIndicatorList.get(mPrePosition).setBackgroundResource(R.drawable.banner_dot_normal);
        mIndicatorList.get(position % mIndicatorList.size()).setBackgroundResource(R.drawable.banner_dot_select);
        mPrePosition = position % mIndicatorList.size();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged(),state = " + state);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRoll();
    }

    private class BannerAdapter extends PagerAdapter {

        private List<ImageView> imgCache = new ArrayList<ImageView>();

        @Override
        public int getCount() {
            Log.d(TAG, "getCount()");
            //无限滑动
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            Log.d(TAG, "isViewFromObject()");
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Log.d(TAG, "instantiateItem()");
            ImageView iv;

            //获取ImageView对象
            if (imgCache.size() > 0) {
                iv = imgCache.remove(0);
            } else {
                iv = new ImageView(mContext);
            }
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            iv.setOnTouchListener(new OnTouchListener() {
                private int downX = 0;
                private long downTime = 0;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            stopRoll();
                            //获取按下的x坐标
                            downX = (int) v.getX();
                            downTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
                            startRoll();
                            int moveX = (int) v.getX();
                            long moveTime = System.currentTimeMillis();
                            if (downX == moveX && (moveTime - downTime < 500)) {//点击的条件
                                //轮播图回调点击事件
                                mBannerClickListener.bannerClick(position % mDataList.size());
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            startRoll();
                            break;
                    }
                    return true;
                }
            });

            //加载图片
            // Glide.with(mContext).load(mDataList.get(position % mDataList.size())).error(R.mipmap.ic_launcher).into(iv);
            iv.setImageResource(R.mipmap.ic_launcher);
            ((ViewPager) container).addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d(TAG, "destroyItem()");
            if (object != null && object instanceof ImageView) {
                ImageView iv = (ImageView) object;
                ((ViewPager) container).removeView(iv);
                imgCache.add(iv);
            }
        }
    }

    public interface BannerClickListener {
        void bannerClick(int position);
    }
}
