package com.android.huirongzhang.todo.util;

import android.content.res.Resources;

/**
 * Created by HuirongZhang
 * on 28/02/2017.
 * 支持多种屏幕：
 * 1.系统会进行缩放和大小调整，适用于不同的屏幕
 * 2.开发者进行优化，(对不同的屏幕尺寸和密度)
 *
 */

public class UIUtils {

	public static float dp2px(Resources resources, float dp) {
		//get the screen's density scale
		//根据当前屏幕密度指定 将 dp 单位转换为像素必须使用的缩放系数.
		final float scale = resources.getDisplayMetrics().density;
		//convert the dps to pixels,based on density scale
		//然后在转换时加上 0.5f，将该数字四舍五入到最接近的整数
		return dp * scale + 0.5f;
	}

	public static float sp2px(Resources resources, float sp) {
		final float scale = resources.getDisplayMetrics().scaledDensity;
		return sp * scale;
	}
}
