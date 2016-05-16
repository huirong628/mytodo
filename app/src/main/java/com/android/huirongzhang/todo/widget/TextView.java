package com.android.huirongzhang.todo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.nio.Buffer;

/**
 * Created by zhanghuirong on 2016/5/3.
 */
public class TextView extends View {

    private Editor mEditor;
    private CharSequence mText;
    private CharSequence mTransformed;
    private BufferType mBufferType = BufferType.NORMAL;//初始值

    private Layout mLayout;
    private final TextPaint mTextPaint;

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mText = "";
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    }

    private void createEditorIfNeeded() {
        if (mEditor == null) {
            mEditor = new Editor(this);
        }
    }

    public void setTextIsSelectable(boolean selectable) {//default is false
        if (!selectable && mEditor == null) {
            return;
        }
        createEditorIfNeeded();
        if (mEditor.mTextIsSelectable == selectable) {//selectable is false,因为mTextIsSelectable默认是false
            return;
        }
        mEditor.mTextIsSelectable = selectable;

        setText(mText, selectable ? BufferType.SPANNABLE : BufferType.NORMAL);
    }

    public boolean isTextSelectable() {
        return mEditor == null ? false : mEditor.mTextIsSelectable;
    }

    public void setText(CharSequence text) {
        setText(text, mBufferType);
    }

    private void setText(CharSequence text, BufferType type) {
        setText(text, type, true, 0);
    }

    private void setText(CharSequence text, BufferType type, boolean notifyBefore, int oldLen) {
        if (text == null) {
            text = "";
        }
        mText = text;
        mBufferType = type;
    }

    public final void setText(int resId) {
        //getContext(): get this view's (TextView) context.
        //getContext() 父类View中的方法，通过构造函数传进来的public View(Context context){}
        //TextView的Context最后传递给View的Context
        setText(getContext().getResources().getText(resId));
    }

    //定义为final的方法不能被重写
    public final void setText(int resId, BufferType type) {
        setText(getContext().getResources().getText(resId), type);
    }

    //step one: measure
    private static final BoringLayout.Metrics UNKNOWN_BORING = new BoringLayout.Metrics();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Mode type :UNSPECIFIED,EXACTLY,AT_MOST
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        BoringLayout.Metrics boring = UNKNOWN_BORING;

        int des = -1;
        if (widthMode == MeasureSpec.EXACTLY) {//指定为具体数值或者为match_parent
            width = widthSize;
        } else {
            width = 0;
            if (mLayout != null) {
                des = desired(mLayout);
            }
            if (des < 0) {
                //boring = BoringLayout.isBoring(mTransformed, mTextPaint, mTextDir, mBoring);
            }
            width = Math.max(width, getSuggestedMinimumWidth());
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = Math.max(0, getSuggestedMinimumHeight());
        }
        //最后一定要调用View的如下方法
        //to store the measured width and height of this view
        setMeasuredDimension(width, height);
    }

    //step two: layout
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //step three: draw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private static int desired(Layout layout) {
        int n = layout.getLineCount();
        CharSequence text = layout.getText();
        float max = 0;
        for (int i = 0; i < n; i++) {
            if (text.charAt(layout.getLineEnd(i) - 1) != '\n') {
                return -1;
            }
        }
        for (int i = 0; i < n; i++) {
            max = Math.max(max, layout.getLineWidth(i));
        }
        return (int) Math.ceil(max);//max向上取整
    }

    public enum BufferType {
        NORMAL, SPANNABLE, EDITABLE,
    }
}
