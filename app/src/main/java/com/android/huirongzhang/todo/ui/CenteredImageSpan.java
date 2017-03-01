package com.android.huirongzhang.todo.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Created by HuirongZhang
 * on 01/03/2017.
 */

public class CenteredImageSpan extends ImageSpan {

	public CenteredImageSpan(Drawable drawable) {
		this(drawable, ALIGN_BOTTOM);
	}

	public CenteredImageSpan(Drawable drawable, int verticalAligment) {
		super(drawable, verticalAligment);
	}

	@Override
	public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
		Drawable drawable = getDrawable();
		Rect rect = drawable.getBounds();
		/*if (fm != null) {
			Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
			int fontHeight = fmPaint.descent - fmPaint.ascent;
			int drHeight = rect.bottom - rect.top;
			int centerY = fmPaint.ascent + fontHeight / 2;

			fm.ascent = centerY - drHeight / 2;
			fm.top = fm.ascent;
			fm.bottom = centerY + drHeight / 2;
			fm.descent = fm.bottom;
		}*/
		if (fm != null) {
			Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
			int fontHeight = fmPaint.bottom - fmPaint.top;
			int drHeight = rect.bottom - rect.top;
			int top = drHeight / 2 - fontHeight / 4;
			int bottom = drHeight / 2 + fontHeight / 4;

			fm.ascent = -bottom;
			fm.top = -bottom;
			fm.bottom = top;
			fm.descent = top;
		}
		return rect.right;
	}

	@Override
	public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
		/*Drawable drawable = getDrawable();
		canvas.save();
		Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
		int fontHeight = fmPaint.descent - fmPaint.ascent;
		int centerY = y + fmPaint.descent - fontHeight / 2;
		int transY = centerY - (drawable.getBounds().bottom - drawable.getBounds().top) / 2;
		canvas.translate(x, transY);
		drawable.draw(canvas);
		canvas.restore();*/
		Drawable d = getDrawable();
		Paint.FontMetricsInt fm = paint.getFontMetricsInt();
		int transY = y + (fm.descent + fm.ascent - d.getBounds().bottom) / 2;
		canvas.save();
		canvas.translate(x, transY);
		d.draw(canvas);
		canvas.restore();
	}
}