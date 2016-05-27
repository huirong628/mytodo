package com.android.huirongzhang.todo.searchview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;

/**
 * Created by zhanghuirong on 2016/5/24.
 */
public class SearchView extends FrameLayout implements View.OnClickListener {

    public static final int VERSION_TOOLBAR = 1000;
    public static final int VERSION_MENU_ITEM = 1001;
    public static final int VERSION_MARGINS_TOOLBAR_BIG = 1002;

    private Context mContext;

    private ImageView mBackView;
    private EditText mInputView;
    private ImageView mClearView;
    private ImageView mVoiceView;

    private RecyclerView mRecyclerView;
    private View mShadowView;
    private View mDividerView;

    private CharSequence mUserQuery;
    private SearchAdapter mSearchAdapter;

    private OnQueryTextListener mOnQueryChangeListener = null;
    private String mOldUserQuery;

    private int mVersion = VERSION_TOOLBAR;
    private boolean mIsSearchOpen = false;

    private SearchArrowDrawable mSearchArrow;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        //this(context, attrs, defStyleAttr, 0);
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.search_view, this, true);//该SearchView就是root

        mBackView = (ImageView) findViewById(R.id.image_back);
        mInputView = (EditText) findViewById(R.id.edit_input);

        mClearView = (ImageView) findViewById(R.id.image_clear);
        mClearView.setVisibility(GONE);
        mClearView.setOnClickListener(this);

        mVoiceView = (ImageView) findViewById(R.id.image_voice);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_result);
        /**
         * A LayoutManager must be provided for RecyclerView to function.
         */
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//这个必须设置，否则RecyclerView不显示
        // mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.GONE); // TODO INVISIBLE

        mDividerView = findViewById(R.id.view_divider);
        mDividerView.setVisibility(View.GONE);

        /**
         * This will be called when the enter key is pressed, or when an action supplied to the IME is selected by the user.
         */
        mInputView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                onSubmitQuery();
                return true;
            }
        });

        mInputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SearchView.this.onTextChanged(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mInputView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {//获取focus
                    addFocus();
                } else {
                    removeFocus();
                }
            }
        });

        setVersion(mVersion);
    }

    private void onSubmitQuery() {
        CharSequence query = mInputView.getText();
        mOnQueryChangeListener.onQueryTextSubmit(query.toString());
    }

    private void onTextChanged(CharSequence newText) {
        CharSequence text = mInputView.getText();
        mUserQuery = text;
        if (!TextUtils.isEmpty(text)) {
            mClearView.setVisibility(VISIBLE);//显示删除icon
            mVoiceView.setVisibility(GONE);
        } else {
            mClearView.setVisibility(GONE);//隐藏删除icon
            mVoiceView.setVisibility(VISIBLE);
        }

        if (mOnQueryChangeListener != null && TextUtils.equals(newText, mOldUserQuery)) {
            mOnQueryChangeListener.onQueryTextChange(newText.toString());
        }

        mOldUserQuery = newText.toString();
    }

    public void setOnQueryChangeListener(OnQueryTextListener listener) {
        mOnQueryChangeListener = listener;
    }

    public void setSearchAdapter(SearchAdapter adapter) {
        mSearchAdapter = adapter;
        mRecyclerView.setAdapter(mSearchAdapter);
    }

    public void setVersion(int version) {
        mVersion = version;

        if (mVersion == VERSION_TOOLBAR) {

            mInputView.clearFocus();

            mSearchArrow = new SearchArrowDrawable(mContext);
            mBackView.setImageDrawable(mSearchArrow);
            mVoiceView.setImageResource(R.drawable.search_ic_mic_black_24dp);
            mClearView.setImageResource(R.drawable.search_ic_clear_black_24dp);
        }

        if (mVersion == VERSION_MENU_ITEM) {
            setVisibility(GONE);
            mBackView.setImageResource(R.drawable.search_ic_arrow_back_black_24dp);
            mVoiceView.setImageResource(R.drawable.search_ic_mic_black_24dp);
            mClearView.setImageResource(R.drawable.search_ic_clear_black_24dp);
        }
    }

    public void setVersionMargins(int versionMargins) {
//        CardView.LayoutParams params = new CardView.LayoutParams(
//                CardView.LayoutParams.MATCH_PARENT,
//                CardView.LayoutParams.WRAP_CONTENT
//        );
    }

    private void addFocus() {
        mIsSearchOpen = true;
        showSuggestions();
    }

    private void removeFocus() {
        mIsSearchOpen = false;
        hideSuggestions();
    }

    /**
     * 显示历史列表
     */
    private void showSuggestions() {
        if (mSearchAdapter != null && mRecyclerView.getVisibility() == View.GONE) {
            mDividerView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            // fadeIn(mRecyclerView, mAnimationDuration);
        }
    }

    private void hideSuggestions() {
        if (mSearchAdapter != null && mRecyclerView.getVisibility() == View.VISIBLE) {
            mDividerView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            // fadeIn(mRecyclerView, mAnimationDuration);
        }
    }

    public boolean isSearchOpen() {
        return mIsSearchOpen;
    }

    public void close(boolean animate) {
        if (mInputView.length() > 0) {
            mInputView.getText().clear();
        }
        mInputView.clearFocus();
    }

    public interface OnQueryTextListener {
        boolean onQueryTextChange(String newText);

        boolean onQueryTextSubmit(String query);
    }

    @Override
    public void onClick(View view) {
        if (view == mBackView) {

        } else if (view == mClearView) {
            if (mInputView.length() > 0) {
                mInputView.getText().clear();
            }
        } else if (view == mVoiceView) {

        }
    }
}
