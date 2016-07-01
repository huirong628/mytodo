package com.android.huirongzhang.todo.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.banner.RollBannerView;

import java.util.Arrays;

/**
 * Created by zhanghuirong on 2016/6/30.
 */
public class RollBannerViewTest extends Activity {

    public static String[] imgUrls = {
            "http://image.tianjimedia.com/uploadImages/2014/308/50/G95HTB33CM50_1000x500.jpg",
            "http://p4.yokacdn.com/pic/life/sex/2013/U355P41T8D134686F231DT20130105181832_maxw808.png",
            "http://img.cnmo-img.com.cn/241_800x600/240813.jpg",
            "http://img.gstv.com.cn/material/news/img/640x/2015/04/2015041015122756LR.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f070hyadzkj20p90gwq6v.jpg",
            "http://img.popoho.com/UploadPic/2011-10/20111024132221149.jpg",
            "http://ws3.cdn.caijing.com.cn/2013-10-08/113380221.jpg",
            "http://www.yoka.com/dna/pics/ba1caeb9/97/d35cce9cdb3b11bac9.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_roll_banner_test);
        RollBannerView bannerView = (RollBannerView) findViewById(R.id.banner);
        bannerView.setData(Arrays.asList(imgUrls));
        bannerView.setBannerClickListener(new RollBannerView.BannerClickListener() {
            @Override
            public void bannerClick(int position) {
                Toast.makeText(RollBannerViewTest.this, "banner" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
