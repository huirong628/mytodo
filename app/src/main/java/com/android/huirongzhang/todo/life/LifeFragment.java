package com.android.huirongzhang.todo.life;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public class LifeFragment extends Fragment {

    private PieChartView mChart;
    private PieChartData data;

    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;

    private LinearLayout mChartValue;

    private String[] types = new String[]{"工作", "学习", "生活", "运动"};
    private String[] colors = new String[]{"#33B5E5", "#AA66CC", "#99CC00", "#FFBB33"};
    private float[] valuess = new float[]{30.0f, 40.0f, 10.0f, 20.0f};

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.life_fragment, container, false);
        mChart = (PieChartView) rootView.findViewById(R.id.chart);
        mChartValue = (LinearLayout) rootView.findViewById(R.id.chart_value);
        generateData();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void generateData() {
        int numValues = 6;
        mChartValue.removeAllViews();
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < types.length; ++i) {
            float value = valuess[i];
            int color = Color.parseColor(colors[i]);
            SliceValue sliceValue = new SliceValue(value, color);
            values.add(sliceValue);

            //
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.chart_value_item, null);
            TextView value_tv = (TextView) view.findViewById(R.id.value);
            TextView color_tv = (TextView) view.findViewById(R.id.color);

            value_tv.setText(value + "%");
            color_tv.setText(types[i]);

            Drawable drawable = getResources().getDrawable(R.drawable.shape_chart);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                drawable.setColorFilter(color, PorterDuff.Mode.SRC);
            }
            color_tv.setCompoundDrawables(drawable, null, null, null);

            mChartValue.addView(view);
        }

        data = new PieChartData(values);
//        data.setHasLabels(hasLabels);
//        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
//        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

//        if (isExploded) {
//            data.setSlicesSpacing(24);
//        }

//        if (true) {
//            data.setCenterText1("Hello!");
//
//            // Get roboto-italic font.
//            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//            data.setCenterText1Typeface(tf);
//
//            // Get font size from dimens.xml and convert it to sp(library uses sp values).
//            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
//        }
//
//        if (true) {
//            data.setCenterText2("Charts (Roboto Italic)");
//
//            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//
//            data.setCenterText2Typeface(tf);
//            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
//        }

        mChart.setPieChartData(data);
    }
}
